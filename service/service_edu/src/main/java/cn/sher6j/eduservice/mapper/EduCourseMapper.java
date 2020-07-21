package cn.sher6j.eduservice.mapper;

import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.chapter.CoursePublishVo;
import cn.sher6j.eduservice.entity.frontvo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getFrontCourseInfoById(String courseId);
}
