package cn.sher6j.msmservice.controller;

import cn.sher6j.commonutils.R;
import cn.sher6j.msmservice.service.MsmService;
import cn.sher6j.msmservice.utils.RandomUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信
     * @param phone 目标的手机号
     * @return
     */
    @ApiOperation("发送短信的方法")
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //1 从redis获取验证码，如果获取到直接放回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //2 若redis中获取不到，则进行阿里云发送
        //生成随机值作为验证码，将验证码传递给阿里云进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信
        boolean isSent = msmService.send(param, phone);
        if (isSent) {
            //发动成功，则将发送成功的验证码放到redis中，并设置有效时间
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败！");
        }
    }
}
