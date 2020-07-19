package cn.sher6j.ucenter.service;

import cn.sher6j.ucenter.entity.Member;
import cn.sher6j.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-17
 */
public interface MemberService extends IService<Member> {

    /**
     * 登录
     * @param member 会员
     * @return token序列
     */
    String login(Member member);

    /**
     * 注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据微信openid查询用户
     * @param openid
     * @return
     */
    Member getByOpenId(String openid);
}
