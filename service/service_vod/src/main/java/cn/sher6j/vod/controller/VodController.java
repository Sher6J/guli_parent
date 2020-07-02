package cn.sher6j.vod.controller;

import cn.sher6j.commonutils.R;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import cn.sher6j.vod.service.VodService;
import cn.sher6j.vod.utils.InitVodObject;
import cn.sher6j.vod.utils.VodConstantUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
