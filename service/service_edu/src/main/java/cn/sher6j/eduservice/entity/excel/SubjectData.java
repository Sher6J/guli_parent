package cn.sher6j.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Excel对应的实体类
 * @author sher6j
 * @create 2020-05-17-19:11
 */
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubject;
    @ExcelProperty(index = 1)
    private String twoSubject;
}
