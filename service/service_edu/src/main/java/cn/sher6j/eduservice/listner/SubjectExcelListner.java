package cn.sher6j.eduservice.listner;

import cn.sher6j.eduservice.entity.EduSubject;
import cn.sher6j.eduservice.entity.excel.SubjectData;
import cn.sher6j.eduservice.service.EduSubjectService;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 监听器  没有交给Spring管理
 * @author sher6j
 * @create 2020-05-17-19:14
 */
public class SubjectExcelListner extends AnalysisEventListener<SubjectData> {

    /**
     * 因为SubjectExcelListner不能交给spring进行管理
     * 需要自己new，不能注入其他对象
     * 需要手动注入
     */

    public EduSubjectService subjectService;

    public SubjectExcelListner() {
    }

    public SubjectExcelListner(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 读取Excel内容，一行一行读取，并加到数据库
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }

        //一行一行读取，每次读取都有两个值，第一个值为一级分类，第二个值为二级分类
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubject());
        if (existOneSubject == null) { //没有相同的一级分类， 进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubject());//一级分类名称
            subjectService.save(existOneSubject);
        }
    }

    /**
     * 判断一级分类不能重复添加
     * @param subjectService
     * @param name
     * @return
     */
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    /**
     * 判断二级分类不能重复添加
     * @param subjectService
     * @param name
     * @param pid
     * @return
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
