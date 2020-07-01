package cn.sher6j.vod.controller;

import cn.sher6j.commonutils.R;
import cn.sher6j.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
