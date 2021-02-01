package com.it.fa.controller;

import com.it.fa.model.*;
import com.it.fa.service.article.IArticleService;
import com.it.fa.service.comment.ICommentService;
import com.it.fa.service.label.ILabelService;
import com.it.fa.service.log.ILogService;
import com.it.fa.service.user.IUserService;
import com.it.fa.utils.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 控制跳转首页
 */
@Controller
@RequestMapping("/admin")
public class IndexController {
    @Resource
    private ILogService ILogService;
    @Resource
    private IUserService userService;
    @Resource
    private IArticleService articleService;
    @Resource
    private ICommentService commentService;
    @GetMapping("/index")
    public String toIndex(HttpServletRequest request){
        List<Log> logs = ILogService.findAll();
        List<Articles> articles = articleService.findLately();
        List<Comment> comments = commentService.findLately();
        request.setAttribute("comments",comments);
        request.setAttribute("articles",articles);
        request.setAttribute("logs",logs);
        return "admin/index";
    }
    @GetMapping("/profile")
    public String toPersonalPage(){
        return "admin/profile";
    }
    @ResponseBody
    @PostMapping("/profile")
    public APIResponse saveUserInfo(User u, HttpSession session){
        int k = userService.update(u);
        //更新成功 更新Session
        if(k>0){
            User user = (User) session.getAttribute("userInfo");
            user.setEmail(u.getEmail());
            user.setRealname(u.getRealname());
            session.setAttribute("userInfo",user);
            return APIResponse.success();
        }
        return APIResponse.fail("更新失败");
    }
}
