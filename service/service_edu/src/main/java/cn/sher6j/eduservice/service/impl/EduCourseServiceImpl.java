package cn.sher6j.eduservice.service.impl;

import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.EduCourseDescription;
import cn.sher6j.eduservice.entity.vo.CourseInfoVo;
import cn.sher6j.eduservice.mapper.EduCourseMapper;
import cn.sher6j.eduservice.service.EduCourseDescriptionService;
import cn.sher6j.eduservice.service.EduCourseService;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return 添加课程的id
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表中添加课程基本信息
        //CourseInfoVo转换成EduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse); //影响行数

        if (insert == 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }

        //获取添加之后课程id，将课程id赋值给描述id，这样才能实现课程和课程描述的一对一关系
        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id为课程id
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }
}
