package cn.sher6j.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sher6j
 * @create 2020-07-01-13:31
 */
public interface VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideoToAliyun(MultipartFile file);
}
