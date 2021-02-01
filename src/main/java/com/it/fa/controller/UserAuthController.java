package com.it.fa.controller;

import com.it.fa.constant.LogActions;
import com.it.fa.exception.DefineException;
import com.it.fa.model.*;
import com.it.fa.service.article.IArticleService;
import com.it.fa.service.comment.ICommentService;
import com.it.fa.service.log.ILogService;
import com.it.fa.service.user.IUserService;
import com.it.fa.utils.APIResponse;
import com.it.fa.utils.LoginOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserAuthController {
    @Resource
    private ILogService ILogService;
    @Resource
    private IUserService userService;
    @Resource
    private IArticleService articleService;
    @Resource
    private ICommentService commentService;
    @GetMapping("/login")
    public String toLoginPage(HttpServletRequest request){
        if(LoginOperation.userCookieExists(request)){
            List<Log> logs = ILogService.findAll();
            request.setAttribute("logs",logs);
            List<Articles> articles = articleService.findLately();
            request.setAttribute("articles",articles);
            List<Comment> comments = commentService.findLately();
            request.setAttribute("comments",comments);
            return "admin/index";
        }
        return "admin/login";
    }
    @ResponseBody
    @PostMapping("/login")
    public APIResponse authLogin(@RequestParam(value = "autologin",defaultValue = "off") String autologin,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request, HttpServletResponse response)
    {
        try{
            User userInfo = userService.login(username,password);
            if(!StringUtils.isBlank(autologin)){
                if("on".equals(autologin)){
                    LoginOperation.setCookie(response,username,password);
                }
            }
            request.getSession().setAttribute("userInfo",userInfo);
            ILogService.addLog(LogActions.LOGIN.getAction(),request.getRemoteAddr());
        }catch (Exception e){
            if(e instanceof DefineException){
                return APIResponse.fail(((DefineException) e).getErrorCode());
            }
        }
        return APIResponse.success();
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        System.out.println("logout");
        LoginOperation.deleteCookie(request,response);
        System.out.println(LoginOperation.userCookieExists(request));
        request.getSession().removeAttribute("userInfo");
        return "admin/login";
    }
    @ResponseBody
    @PostMapping("/password")
    public APIResponse updatePwd(HttpSession session,HttpServletRequest request,
            @RequestParam("password") String password, @RequestParam("oldPassword")String oldPassword)
    {
        User u = (User) session.getAttribute("userInfo");
        int k = userService.updatePwd(u,oldPassword,password);
        if(k>0){
            ILogService.addLog(LogActions.UP_PWD.getAction(),request.getRemoteAddr());
            return APIResponse.success();
        }
        return APIResponse.fail("旧密码错误");
    }
}
