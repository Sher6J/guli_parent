package cn.sher6j.ucenter.service.impl;

import cn.sher6j.ucenter.entity.Member;
import cn.sher6j.ucenter.mapper.MemberMapper;
import cn.sher6j.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
