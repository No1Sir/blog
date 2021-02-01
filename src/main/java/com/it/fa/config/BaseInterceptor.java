package com.it.fa.config;

import com.it.fa.model.Info;
import com.it.fa.service.article.IArticleService;
import com.it.fa.utils.AdminCommons;
import com.it.fa.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
        Object userInfo = request.getSession().getAttribute("userInfo");
        //不拦截静态资源 和 登录操作  如果已经登陆就放行
        if (
                uri.startsWith("/admin") && null==userInfo && !uri.startsWith("/admin/login")
                && !uri.startsWith("/admin/logout")
                && !uri.startsWith("/admin/css") && !uri.startsWith("/admin/images")
                && !uri.startsWith("/admin/js") && !uri.startsWith("/admin/plugins")
                && !uri.startsWith("/admin/editormd")
                ) {
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
