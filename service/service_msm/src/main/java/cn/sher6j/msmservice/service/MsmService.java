package cn.sher6j.msmservice.service;

import java.util.Map;

/**
 * @author sher6j
 * @create 2020-07-17-11:06
 */
public interface MsmService {

    /**
     * 发送短信
     * @param param
     * @param phone
     * @return
     */
    boolean send(Map<String, Object> param, String phone);
}
