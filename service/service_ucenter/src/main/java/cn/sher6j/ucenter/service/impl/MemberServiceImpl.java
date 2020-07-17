package cn.sher6j.ucenter.service.impl;

import cn.sher6j.commonutils.JwtUtils;
import cn.sher6j.commonutils.MD5;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import cn.sher6j.ucenter.entity.Member;
import cn.sher6j.ucenter.entity.vo.RegisterVo;
import cn.sher6j.ucenter.mapper.MemberMapper;
import cn.sher6j.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-17
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||
        StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "注册失败！");
        }

        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码错误，注册失败！");
        }

        //判断手机号是否重复
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new GuliException(20001, "手机号已存在，注册失败！");
        }

        //数据添加到数据库中
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/zZfLXcetf2Rpsibq6HbPUWKgWSJHtha9y1XBeaqluPUs6BYicW1FJaVqj7U3ozHd3iaodGKJOvY2PvqYTuCKwpyfQ/132");
        baseMapper.insert(member);
    }
    /**
     * 登录
     * @param member 会员对象，其中封装手机号和密码
     * @return token序列
     */
    @Override
    public String login(Member member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败！");
        }

        //判断手机号是否正确
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new GuliException(20001, "手机号不存在，登录失败！");
        }

        //判断密码，数据库中存储密码肯定是加密的
        //将输入密码进行加密，然后对密文进行对比 -- 加密方式MD5加密
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "密码不正确，登录失败！");
        }

        //判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "用户被禁用，登录失败！");
        }

        //登录成功，生成token序列
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }
}
