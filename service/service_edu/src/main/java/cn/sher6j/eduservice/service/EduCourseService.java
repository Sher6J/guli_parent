package cn.sher6j.eduservice.service;

import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.chapter.CoursePublishVo;
import cn.sher6j.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     * @return 添加课程的id
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程信息
     * @param courseId 课程信息
     * @return
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo coursePublishInfo(String id);
}
