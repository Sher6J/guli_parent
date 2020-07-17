package cn.sher6j.msmservice.service.impl;

import cn.sher6j.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author sher6j
 * @create 2020-07-17-11:06
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信
     * @param param 包含短信信息的集合
     * @param phone 目的手机号码
     * @return 短信是否发送成功
     */
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) return false;

        return false;
    }
}
