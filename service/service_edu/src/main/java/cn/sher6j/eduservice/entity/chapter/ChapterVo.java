package cn.sher6j.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节实体类
 * @author sher6j
 * @create 2020-06-29-9:41
 */
@Data
public class ChapterVo {
    private String id;
    private String title;

    //章节中包含多个小节
    private List<VideoVo> children = new ArrayList<>();
}
