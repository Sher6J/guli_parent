package cn.sher6j.eduservice.controller;


import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduVideo;
import cn.sher6j.eduservice.entity.vo.VideoInfoVo;
import cn.sher6j.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(
            @ApiParam(name = "eduVideo", value = "小节对象", required = true)
            @RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    /**
     * 删除小节
     * @param id
     * @return
     */
    @ApiOperation("删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(
            @ApiParam(name = "id", value = "小节id", required = true)
            @PathVariable String id) {
        boolean isDeleted = videoService.removeById(id);
        if (isDeleted) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

    /**
     * 根据id查询小节
     * @param id 小节id
     * @return
     */
    @ApiOperation("根据小节id查询小节")
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(
            @ApiParam(name = "id", value = "小节id", required = true)
            @PathVariable String id) {
        VideoInfoVo videoInfoVo = videoService.getVideoById(id);
        return R.ok().data("item", videoInfoVo);
    }

    /**
     * 修改小节
     * @param videoInfoVo
     * @param id
     * @return
     */
    @ApiOperation("修改小节")
    @PostMapping("updateVideo/{id}")
    public R updateVideo(
            @ApiParam(name = "videoInfoVo", value = "小节基本信息", required = true)
            @RequestBody VideoInfoVo videoInfoVo,
            @ApiParam(name = "id", value = "小节id", required = true)
            @PathVariable String id) {
        videoService.updateVideoInfoById(videoInfoVo);
        return R.ok();
    }

}

