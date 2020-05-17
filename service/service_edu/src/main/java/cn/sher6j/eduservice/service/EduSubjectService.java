package cn.sher6j.eduservice.service;

import cn.sher6j.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     */
    void addSubject(MultipartFile file, EduSubjectService subjectService);
}
