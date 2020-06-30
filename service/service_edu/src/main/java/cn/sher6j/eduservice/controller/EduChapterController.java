package cn.sher6j.eduservice.controller;


import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduChapter;
import cn.sher6j.eduservice.entity.chapter.ChapterVo;
import cn.sher6j.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 课程大纲列表
     * 根据课程id进行查询所有章节（章节中包含小节）
     * @param courseId 课程id
     * @return
     */
    @ApiOperation("根据课程id查询对应章节及小节")
    @GetMapping("getChapters/{courseId}")
    public R getChapters(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChaptersById(courseId);
        return R.ok().data("chapters", list);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(
            @ApiParam(name = "eduChapter", value = "章节对象", required = true)
            @RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据章节id查询章节信息
     * @param chapterId 章节id
     * @return
     */
    @ApiOperation("根据章节id查询章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(
            @ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    /**
     * 修改章节
     * @param eduChapter
     * @return
     */
    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(
            @ApiParam(name = "eduChapter", value = "章节对象", required = true)
            @RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    /*
    删除这种层级结构（如章节中有小节）时有两种策略：
    1.删除章节时将该章节中的小节都删除
    2.只有删除光该章节对应所有小节后才可以删除章节
     */

    /**
     * 删除章节，用上述的策略2，即章节中不包含小节时才可以删除
     * @param chapterId 章节id
     * @return
     */
    @ApiOperation("删除章节")
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(
            @ApiParam(name = "chapterId", value = "章节id", required = true)
            @PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}