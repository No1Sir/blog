package com.it.fa.controller;

import com.github.pagehelper.PageInfo;
import com.it.fa.model.Articles;
import com.it.fa.model.Comment;
import com.it.fa.service.article.IArticleService;
import com.it.fa.service.comment.ICommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 博客首页展示
 */
@Controller
public class HomeController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IArticleService articleService;
    @Resource
    private ICommentService commentService;
    /**
     * 登陆成功跳转首页
     * @return
     */
    @RequestMapping(value = {"","/index"})
    public String toMyBolg(HttpServletRequest request,
            @RequestParam(name = "page",defaultValue = "1",required = false)Integer page,
            @RequestParam(name = "limit",defaultValue = "3",required = false)Integer limit,
            @RequestParam(name = "type",defaultValue = "photo")String type){
        PageInfo<Articles> articles = articleService.findAllByType(page,limit,type);
        request.setAttribute("articles",articles);
        return "site/index";
    }
    /**
     * 文章内容展示
     */
    @GetMapping("blog/article/{cid}")
    public String getContents(@PathVariable(name = "cid")Integer cid, HttpServletRequest request){
        Articles article = articleService.findArticleById(cid);
        List<Comment> comments = commentService.findAllByCid(cid);
        //每点击一次就自增 数据放在redis中
        stringRedisTemplate.opsForValue().increment("comment_"+cid,1);
        //获取点击量
        String reads = stringRedisTemplate.opsForValue().get("comment_"+cid);
        article.setReads(reads);
        request.setAttribute("article",article);
        request.setAttribute("commentList",comments);
        return "site/blog-details";
    }

    /**
     * 获取博客数据
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping("/blog/index")
    public String showBlogsOfArticle(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,
                            @RequestParam(name = "type",defaultValue = "post") String type,
                            HttpServletRequest request){
        PageInfo<Articles> articlesPageInfo = articleService.findAllByType(page,limit,type);
        request.setAttribute("articles",articlesPageInfo);
        return "site/blog";
    }
    /**
     * 关于博客
     */
    @GetMapping(value = {"/about", "/about/index"})
    public String getAbout(HttpServletRequest request){
        request.setAttribute("active","about");
        return "site/about";
    }
}
