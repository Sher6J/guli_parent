package cn.sher6j.eduservice.service.impl;

import cn.sher6j.eduservice.client.VodClient;
import cn.sher6j.eduservice.entity.EduVideo;
import cn.sher6j.eduservice.entity.vo.VideoInfoVo;
import cn.sher6j.eduservice.mapper.EduVideoMapper;
import cn.sher6j.eduservice.service.EduVideoService;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    /**
     * 根据id查询并包装小节信息
     * @param id
     * @return
     */
    @Override
    public VideoInfoVo getVideoById(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        if (eduVideo == null) throw new GuliException(20001, "数据不存在！");

        VideoInfoVo videoInfoVo = new VideoInfoVo();
        BeanUtils.copyProperties(eduVideo, videoInfoVo);
        return videoInfoVo;
    }

    /**
     * 更新课程信息
     * @param videoInfoVo
     */
    @Override
    public void updateVideoInfoById(VideoInfoVo videoInfoVo) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoVo, eduVideo);
        int update = baseMapper.updateById(eduVideo);
        if (update == 0) throw new GuliException(20001, "课时信息保存失败！");
    }

    /**
     * 根据课程id删除小节
     * @param courseId 课程id
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        //1根据课程id查询出所有视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
        //List<EduVideo> -> List<String>
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (StringUtils.isEmpty(videoSourceId)) videoIds.add(videoSourceId);
        }

        if (videoIds.size() > 0) vodClient.deleteBatch(videoIds);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
