package cn.sher6j.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sher6j
 * @create 2020-05-17-18:46
 */
public class TestEasyExcel {
    /**
     * 测试Excel写操作
     */
    @Test
    public void testWrite() {
        //1.设置写入文件夹地址和Excel文件名称
        String filename = "D:\\write.xlsx";
        //2.调用easyExcel中方法实现写操作
        //write方法两个参数，1.文件路径名称 2.实体类class（实体类属性对应Excel中的列）
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());
    }

    /**
     * 测试Excel读操作
     */
    @Test
    public void testRead() {
        String filename = "D:\\write.xlsx";
        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    /**
     * 创建Excel数据
     * @return
     */
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("六甲" + i);
            list.add(demoData);
        }
        return list;
    }
}
