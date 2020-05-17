package cn.sher6j.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author sher6j
 * @create 2020-05-17-18:57
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {
    /**
     * 一行一行读取Excel内容（表头不读）
     * @param demoData
     * @param analysisContext
     */
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("====" + demoData + "====");
    }

    /**
     * 读取表头内容
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    /**
     * 读取数据完成后的操作
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
