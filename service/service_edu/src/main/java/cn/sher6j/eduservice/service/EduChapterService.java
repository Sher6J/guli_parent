package cn.sher6j.eduservice.service;

import cn.sher6j.eduservice.entity.EduChapter;
import cn.sher6j.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id查询所有章节（章节中包含小节）
     * @param courseId 课程id
     * @return
     */
    List<ChapterVo> getChaptersById(String courseId);

    /**
     * 删除章节
     * @param chapterId
     */
    boolean deleteChapter(String chapterId);
}
