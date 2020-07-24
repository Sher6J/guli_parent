package cn.sher6j.eduorder.client;

import cn.sher6j.commonutils.ordervo.CourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author sher6j
 * @create 2020-07-24-19:22
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    @PostMapping("/eduservice/coursefront/getCourseInfo{id}")
    public CourseVo getCourseInfo(@PathVariable("id") String id);
}
