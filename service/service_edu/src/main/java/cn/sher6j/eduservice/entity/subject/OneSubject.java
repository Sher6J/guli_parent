package cn.sher6j.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类实体类
 * 关系：一个一级分类有多个二级分类
 * @author sher6j
 * @create 2020-05-18-14:16
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();
}
