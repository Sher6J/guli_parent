package cn.sher6j.eduservice.controller.front;

import cn.sher6j.commonutils.JwtUtils;
import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.client.UCenterClient;
import cn.sher6j.eduservice.entity.EduComment;
import cn.sher6j.eduservice.entity.Member;
import cn.sher6j.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sher6j
 * @create 2020-07-21-12:42
 */
@RestController
@RequestMapping("/eduservice/comment")
public class CommentFrontController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UCenterClient uCenterClient;

    @ApiOperation("查询评论分页列表")
    @GetMapping("getComments/{page}/{limit}")
    public R getComments(@PathVariable long page, @PathVariable long limit,
                         @ApiParam(name = "courseQuery", value = "查询对象", required = false) String courseId) {
        Page<EduComment> commentPage = new Page<>();
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);

        commentService.page(commentPage, wrapper);
        List<EduComment> commentList = commentPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", commentPage.getCurrent());
        map.put("pages", commentPage.getPages());
        map.put("size", commentPage.getSize());
        map.put("total", commentPage.getTotal());
        map.put("hasNext", commentPage.hasNext());
        map.put("hasPrevious", commentPage.hasPrevious());

        return R.ok().data(map);
    }

    @ApiOperation("添加评论")
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().message("请登录后评论！");
        }
        comment.setMemberId(memberId);

        Member member = uCenterClient.getUserInfo(memberId);

        comment.setNickname(member.getNickname());
        comment.setAvatar(member.getAvatar());

        commentService.save(comment);
        return R.ok();
    }
}
