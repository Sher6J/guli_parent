package cn.sher6j.eduservice.client;

import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author sher6j
 * @create 2020-07-21-12:36
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UCenterClientImpl.class)
public interface UCenterClient {
    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/ucenter/member/getUserInfo/{id}")
    public Member getUserInfo(@PathVariable String id);
}
