package cn.sher6j.vod.service.impl;

import cn.sher6j.vod.service.VodService;
import cn.sher6j.vod.utils.VodConstantUtils;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sher6j
 * @create 2020-07-01-13:32
 */
@Service
public class VodServiceImpl implements VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return 视频id
     */
    @Override
    public String uploadVideoToAliyun(MultipartFile file) {
        String fileName = file.getOriginalFilename(); //如 test.mp4
        String title = fileName.substring(0, fileName.lastIndexOf("."));// 得到 test
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //accessKeyId, accessKeySecret
        //title：上传文件在阿里云中的名称
        //fileName：上传文件原始名称
        //inputStream：上传文件输入流
        UploadStreamRequest request = new UploadStreamRequest(
                VodConstantUtils.ACCESS_KEY_ID, VodConstantUtils.ACCESS_KEY_SECRET,
                title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        String videoId = response.getVideoId();
        return videoId;
    }
}
