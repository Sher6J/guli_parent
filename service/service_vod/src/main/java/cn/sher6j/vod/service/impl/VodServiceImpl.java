package cn.sher6j.vod.service.impl;

import cn.sher6j.commonutils.R;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import cn.sher6j.vod.service.VodService;
import cn.sher6j.vod.utils.InitVodObject;
import cn.sher6j.vod.utils.VodConstantUtils;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    /**
     * 删除多个视频
     * @param videoList
     */
    @Override
    public void removeAliyunVideos(List<String> videoList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodObject.initVodClient(VodConstantUtils.ACCESS_KEY_ID, VodConstantUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //videoList转换成 1,2,3 这种形式
            String videoLists = StringUtils.join(videoList.toArray(), ",");
            //向request中设置视频id（可以传多个id）
            request.setVideoIds(videoLists);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }
}
