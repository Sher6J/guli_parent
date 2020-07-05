package cn.sher6j.eduservice.client;

import cn.sher6j.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 调用微服务接口
 * @author sher6j
 * @create 2020-07-03-20:45
 */
@Component
@FeignClient("service-vod")
public interface VodClient {
    //定义调用的方法路径
    @DeleteMapping("/eduvod/video/removeVideoFromAliyun/{id}")
    public R removeVideoFromAliyun(@PathVariable("id") String id);

    //定义调用删除多个视频的方法
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
