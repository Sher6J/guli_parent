package cn.sher6j.msmservice.controller;

import cn.sher6j.commonutils.R;
import cn.sher6j.msmservice.service.MsmService;
import cn.sher6j.msmservice.utils.RandomUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sher6j
 * @create 2020-07-17-11:06
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    /**
     * 发送短信
     * @param phone 目标的手机号
     * @return
     */
    @ApiOperation("发送短信的方法")
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //生成随机值作为验证码，将验证码传递给阿里云进行发送
        String code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信
        boolean isSent = msmService.send(param, phone);
        if (isSent) return R.ok();
        else return R.error().message("短信发送失败！");
    }
}
