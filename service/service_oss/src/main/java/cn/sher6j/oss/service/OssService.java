package cn.sher6j.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sher6j
 * @create 2020-05-17-14:48
 */
public interface OssService {
    /**
     * 上传头像到OSS
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
