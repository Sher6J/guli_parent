package cn.sher6j.eduservice.service;

import cn.sher6j.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-13
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师并用Map封装分页信息
     * @param teacherPage
     * @return
     */
    Map<String, Object> getFrontTeacherList(Page<EduTeacher> teacherPage);
}
