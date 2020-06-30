package cn.sher6j.eduservice.service.impl;

import cn.sher6j.eduservice.entity.EduChapter;
import cn.sher6j.eduservice.entity.EduVideo;
import cn.sher6j.eduservice.entity.chapter.ChapterVo;
import cn.sher6j.eduservice.entity.chapter.VideoVo;
import cn.sher6j.eduservice.mapper.EduChapterMapper;
import cn.sher6j.eduservice.service.EduChapterService;
import cn.sher6j.eduservice.service.EduVideoService;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 根据课程id查询所有章节
     * @param courseId 课程id
     * @return
     */
    @Override
    public List<ChapterVo> getChaptersById(String courseId) {

        //1.根据课程id查询课程里面的所有章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterQueryWrapper);

        /**
         * 当前在EduChapterService中，baseMapper是用来查EduChapter的，查不了EduVideo
         * 所以需要对EduVideoService进行注入
         */

        //2.根据课程id查询课程里面的所有小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);

        //创建list集合，用于最终封装数据
        List<ChapterVo> list = new ArrayList<>();

        //3.遍历查询章节list集合进行封装
        //遍历查询章节list集合
        eduChapterList.forEach(eduChapter -> {
            //每个EduChapter对象的值复制到ChapterVo对象中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            //创建集合，用于封装章节对应的小节
            List<VideoVo> videoVoList = new ArrayList<>();

            //4.遍历查询小节list集合进行封装
            eduVideoList.forEach(eduVideo -> {
                //判断：小节中的chapter_id和章节里面id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            });

            chapterVo.setChildren(videoVoList);
            list.add(chapterVo);
        });

        return list;
    }

    /**
     * 删除章节
     * @param chapterId 章节id
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterId查询小结表（video），若能查询出数据，则不删除章节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        //不需要得到小节具体数据，只需要判断有没有小节
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "该章节中还有小节，不能删除");
        } else {
            int delete = baseMapper.deleteById(chapterId);
            return delete > 0;
        }
    }

    /**
     * 根据课程id删除章节
     * @param courseId 课程id
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}