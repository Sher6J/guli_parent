package cn.sher6j.ucenter.controller;


import cn.sher6j.commonutils.JwtUtils;
import cn.sher6j.commonutils.R;
import cn.sher6j.commonutils.ordervo.OrderMember;
import cn.sher6j.ucenter.entity.Member;
import cn.sher6j.ucenter.entity.vo.RegisterVo;
import cn.sher6j.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 注册接口
     * @param registerVo 注册对象
     * @return
     */
    @ApiOperation("注册接口")
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 登录接口
     * @param member 会员对象，其中封装手机号和密码
     * @return
     */
    @ApiOperation("登录接口")
    @PostMapping("login")
    public R loginUser(@RequestBody Member member) {
        //调用service方法实现登录，返回token值（JWT生成）
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @ApiOperation("根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(id);
        return R.ok().data("memberInfo", member);
    }

    /**
     * 根据用户id获取用户信息，可用作在edu模块和order模块的远程调用
     * @param id
     * @return
     */
    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("getUserInfo/{id}")
    public OrderMember getUserInfo(@PathVariable String id) {
        Member member = memberService.getById(id);
        //把member对象中的值赋值给OrderMember中
        OrderMember orderMember = new OrderMember();
        BeanUtils.copyProperties(member, orderMember);
        return orderMember;
    }
}

