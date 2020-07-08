package cn.sher6j.eduservice.client;

import cn.sher6j.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 容错后的方法
 * @author sher6j
 * @create 2020-07-08-13:53
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeVideoFromAliyun(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频出错");
    }
}
