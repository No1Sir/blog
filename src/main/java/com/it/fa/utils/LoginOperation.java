package com.it.fa.utils;

import com.it.fa.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录、登出用户Cookie信息
 */
public class LoginOperation {
    public static void setCookie(HttpServletResponse response,String username,String pwd){
        Cookie cookie = new Cookie("user_name",username);
        Cookie cookie1 = new Cookie("user_pwd",pwd);
        cookie.setMaxAge(30*60);
        cookie1.setMaxAge(30*60);
        response.addCookie(cookie);
        response.addCookie(cookie1);
    }
    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if ("user_name".equals(c.getName()) || "user_pwd".equals(c.getName())) {
                Cookie cookie = new Cookie(c.getName(),null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    public static boolean userCookieExists(HttpServletRequest request) {
        int t = 0;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if ("user_name".equals(c.getName())||"user_pwd".equals(c.getName())) {
                return true;
            }
        }
        return false;
    }
}
