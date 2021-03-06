package com.it.fa.config;

import com.it.fa.model.Info;
import com.it.fa.model.User;
import com.it.fa.service.article.IArticleService;
import com.it.fa.utils.AdminCommons;
import com.it.fa.utils.Common;
import com.it.fa.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BaseInterceptor implements HandlerInterceptor {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private Common common;
    @Autowired
    private AdminCommons adminCommons;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        User userInfo = (User) request.getSession().getAttribute("userInfo");
        if (null==userInfo && !uri.equals("/") && !uri.equals("/index") && !uri.equals("/admin/login")
                && !uri.startsWith("/blog")
                && !uri.equals("/about/index")&& !uri.equals("/about"))
        {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Info info = articleService.findArticleBaseInfo();
        request.setAttribute("commons",common);
        request.setAttribute("adminCommons",adminCommons);
        request.setAttribute("info",info);
    }
}
