package cn.sher6j.eduservice.entity.chapter;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sher6j
 * @create 2020-06-29-22:03
 */
@Data
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String oneSubject;
    private String twoSubject;
    private String teacherName;
    private String price;//只用于显示
}
