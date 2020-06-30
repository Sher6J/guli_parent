package cn.sher6j.eduservice.service;

import cn.sher6j.eduservice.entity.EduVideo;
import cn.sher6j.eduservice.entity.vo.VideoInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据id查询小节信息
     * @param id
     * @return
     */
    VideoInfoVo getVideoById(String id);

    /**
     * 更新小节信息
     * @param videoInfoVo
     */
    void updateVideoInfoById(VideoInfoVo videoInfoVo);

    /**
     * 根据课程id删除小节
     * @param courseId 课程id
     */
    void removeVideoByCourseId(String courseId);
}
