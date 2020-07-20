package cn.sher6j.eduservice.service.impl;

import cn.sher6j.eduservice.entity.EduTeacher;
import cn.sher6j.eduservice.mapper.EduTeacherMapper;
import cn.sher6j.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-13
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 分页查询讲师
     * @param teacherPage
     * @return
     */
    @Override
    public Map<String, Object> getFrontTeacherList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //分页数据封装到teacherPage对象中
        baseMapper.selectPage(teacherPage, wrapper);

        List<EduTeacher> records = teacherPage.getRecords();
        long current = teacherPage.getCurrent();//当前页
        long pages = teacherPage.getPages();//总页数
        long size = teacherPage.getSize();//每页记录数
        long total = teacherPage.getTotal();//总记录数
        boolean hasNext = teacherPage.hasNext();//当前是否有下一页
        boolean hasPrevious = teacherPage.hasPrevious();//当前是否有上一页

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
