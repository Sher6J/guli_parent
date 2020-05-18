package cn.sher6j.eduservice.service;

import cn.sher6j.eduservice.entity.EduCourse;
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
     */
    void saveCourseInfo(CourseInfoVo courseInfoVo);
}
