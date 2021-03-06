package cn.sher6j.vod.controller;

import cn.sher6j.commonutils.R;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import cn.sher6j.vod.service.VodService;
import cn.sher6j.vod.utils.InitVodObject;
import cn.sher6j.vod.utils.VodConstantUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author sher6j
 * @create 2020-07-01-13:30
 */
@Api(description = "阿里云视频操作")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return 视频在阿里云中的id
     */
    @ApiOperation("上传视频到阿里云")
    @PostMapping("uploadVideoToAliyun")
    public R uploadVideoToAliyun(MultipartFile file) {
        String videoId = vodService.uploadVideoToAliyun(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 删除视频
     * @param id 视频在阿里云中的id
     * @return
     */
    @ApiOperation("根据视频id删除阿里云中的视频")
    @DeleteMapping("removeVideoFromAliyun/{id}")
    public R removeVideoFromAliyun(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodObject.initVodClient(VodConstantUtils.ACCESS_KEY_ID, VodConstantUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置视频id（可以传多个id）
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    /**
     * 删除多个视频
     * @param videoList
     * @return
     */
    @ApiOperation("删除多个阿里云中的视频")
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {
        vodService.removeAliyunVideos(videoList);
        return R.ok();
    }

    @ApiOperation("根据视频id获取视频播放凭证")
    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId) {
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodObject.initVodClient(VodConstantUtils.ACCESS_KEY_ID, VodConstantUtils.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId); //向request中设置视频id
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);//调用方法得到凭证
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
