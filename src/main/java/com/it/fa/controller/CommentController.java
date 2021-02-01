package com.it.fa.controller;

import com.github.pagehelper.PageInfo;
import com.it.fa.model.Comment;
import com.it.fa.service.comment.ICommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/comments")
public class CommentController {
    @Resource
    private ICommentService commentService;
    @GetMapping("")
    public String toCommentList(HttpServletRequest request, @RequestParam(name = "limit",defaultValue = "5")int limit,
                                @RequestParam(name = "page",defaultValue = "1")int page){
        PageInfo<Comment> comments = commentService.findAll(page,limit);
        request.setAttribute("comments",comments);
        return "admin/comment_list";
    }
}
