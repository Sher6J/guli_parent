package cn.sher6j.eduorder.client;

import cn.sher6j.commonutils.ordervo.OrderMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author sher6j
 * @create 2020-07-24-19:22
 */
@Component
@FeignClient("service-ucenter")
public interface UCenterClient {

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/ucenter/member/getUserInfo/{id}")
    public OrderMember getUserInfo(@PathVariable("id") String id);
}
