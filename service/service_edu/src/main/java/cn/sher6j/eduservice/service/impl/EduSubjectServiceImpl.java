package cn.sher6j.eduservice.service.impl;

import cn.sher6j.eduservice.entity.EduSubject;
import cn.sher6j.eduservice.entity.excel.SubjectData;
import cn.sher6j.eduservice.entity.subject.OneSubject;
import cn.sher6j.eduservice.entity.subject.TwoSubject;
import cn.sher6j.eduservice.listner.SubjectExcelListner;
import cn.sher6j.eduservice.mapper.EduSubjectMapper;
import cn.sher6j.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file
     */
    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListner(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 课程分类列表
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1 查询出所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);

        //2 查询出所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> list = new ArrayList<>();

        //3 封装一级分类
        /**
         * 查询出所有的一级分类list集合遍历，
         * 得到每个一级分类对象，获取每个一级分类对象值，
         * 封装到要求的list集合中
         */
        oneSubjects.forEach(eduSubject -> {
            //把eduSubject中的值获取出来，放到OneSubject对象中
            //将OneSubject对象放到list中
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject, oneSubject);//效果和上面两行相同

            //4 封装二级分类
            //在一级分类循环遍历查询多有的二级分类
            //创建集合封装每个一级分类的二级分类
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            twoSubjects.forEach(eduSubject2 -> {
                //判断二级分类parentID和一级分类的id是否一样
                if (eduSubject2.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            });

            //把一级分类下面的所有二级分类放到一级分类里
            oneSubject.setChildren(twoSubjectList);

            list.add(oneSubject);
        });

        return list;
    }
}
