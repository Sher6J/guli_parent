package cn.sher6j.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Excel对应的实体类
 * @author sher6j
 * @create 2020-05-17-18:44
 */
@Data
public class DemoData {
    //设置Excel中的表头名称
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;
}
