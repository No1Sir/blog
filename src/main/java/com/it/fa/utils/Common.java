package com.it.fa.utils;

import com.it.fa.constant.WebConst;
import com.it.fa.model.Info;
import com.it.fa.service.article.IArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Component
public class Common {
    /**
     * 用于返回登陆页面的随机背景图片uri
     * @param max
     * @param url
     * @return
     */
    public static String random(Integer max,String url){
        return (new Random().nextInt(max)+1)+url;
    }
    /**
     * 将时间戳转化为Date类型
     */
    public static String dateFormat(Integer unix_time_stamp,String pattern){
        if(null != unix_time_stamp && !StringUtils.isBlank(pattern)){
            return DateKit.formatDateByUnixTime(unix_time_stamp,pattern);
        }
        return "";
    }
    /**
     * 显示文章内容，转换markdown为html
     *
     * @param value
     * @return
     */
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            value = value.replace("<!-- more -->", "\r\n");
            return Tales.mdToHtml(value);
        }
        return "";
    }
    /**
     * blog随机配图
     * @return
     */
    public static String randomBlogPic(){
        return "/site/images/blog-images/blog-" + random(13)+".jpg";
    }
    /**
     * 指定范围随机数
     */
    public static Integer random(Integer min,Integer max){
        return (new Random().nextInt(max+1)+min);
    }
    public static Integer random(Integer max){
        return (new Random().nextInt(max+1));
    }
    /**
     * 返回文章链接地址
     *
     * @param cid
     * @return
     */
    public static String articleLinks(Integer cid) {
        return "/blog/article/"+cid;
    }
    /**
     * 获取社交的链接地址
     *
     * @return
     */
    public static Map<String, String> social() {
        final String prefix = "social_";
        Map<String, String> map = new HashMap<>();
        map.put("csdn", WebConst.initConfig.get(prefix + "csdn"));
        map.put("jianshu", WebConst.initConfig.get(prefix + "jianshu"));
        map.put("resume", WebConst.initConfig.get(prefix + "resume"));
        map.put("weibo", WebConst.initConfig.get(prefix + "weibo"));
        map.put("zhihu", WebConst.initConfig.get(prefix + "zhihu"));
        map.put("github", WebConst.initConfig.get(prefix + "github"));
        map.put("twitter", WebConst.initConfig.get(prefix + "twitter"));
        return map;
    }
}
