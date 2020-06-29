package cn.sher6j.eduservice.controller;


import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduVideo;
import cn.sher6j.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public R addVideo(@RequestBody EduVideo eduVideo) {
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
    public R deleteVideo(@PathVariable String id) {
        videoService.removeById(id);
        return R.ok();
    }

    /**
     * 修改小节
     * @param eduVideo
     * @return
     */
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

