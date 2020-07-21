package cn.sher6j.eduservice.entity.chapter;

import lombok.Data;

import java.io.Serializable;

/**
 * 小节实体类
 * @author sher6j
 * @create 2020-06-29-9:41
 */
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String videoSourceId;
}
