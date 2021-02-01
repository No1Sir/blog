package com.it.fa.controller;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.it.fa.model.Articles;
import com.it.fa.model.Label;
import com.it.fa.service.article.IArticleService;
import com.it.fa.service.label.ILabelService;
import com.it.fa.service.relation.IRelationService;
import com.it.fa.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    @Resource
    private ILabelService labelService;
    @Resource
    private IArticleService articleService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 按页查询文章
     */
    @GetMapping(value = {"","/"})
    public String toArticlesList( @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                  @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,
                                  HttpServletRequest request) {
        PageInfo<Articles> articles = articleService.findAll(page, limit);
        request.setAttribute("articles", articles);
        return "admin/article_list";
    }
    /**
     * 发布文章
     */
    @ResponseBody
    @PostMapping("/publish")
    public APIResponse addArticles(Articles articles){
        //草稿只保存文章基础信息
        if("draft".equals(articles.getStatus())){
            articleService.updateArticle(articles);
            return APIResponse.success();
        }
        //添加文章
        int i = articleService.addArticles(articles);
        //如果文章是博文 需要记录标签对应博文的关系
        if("post".equals(articles.getType())){
            articleService.addRelation(articles);
        }
        //添加成功 对应阅读量计入Redis 阅读量初始为0
        if(i>0){
            stringRedisTemplate.opsForValue().set("comment_"+articles.getCid(),"0");
            return APIResponse.success();
        }
        return APIResponse.fail("新增失败!!");
    }
    /**
     * 跳转至发布文章页面
     * 修改发布页面2合1
     * 根据有无具体文章内容来区分是新发布还是需要修改
     */
    @GetMapping("/publish")
    public String toPublish(HttpServletRequest request){
        List<Label> categories = labelService.findAllByCategory();
        request.setAttribute("categories",categories);
        return "admin/article_edit";
    }
    /**
     * 跳转至修改文章页面
     */
    @GetMapping("/{cid}")
    public String editArticle(@PathVariable(name = "cid")Integer cid, HttpServletRequest request){
        List<Label> categories = labelService.findAllByCategory();
        request.setAttribute("categories",categories);
        //在数据库中查找
        Articles contents = articleService.findArticleById(cid);
        request.setAttribute("contents",contents);
        return "admin/article_edit";
    }
    /**
     * 修改文章
     */
    @ResponseBody
    @PostMapping("/modify")
    public APIResponse modifyArticle(Articles articles){
        if("draft".equals(articles.getStatus())){
            articleService.updateArticle(articles);
            return APIResponse.success();
        }
        //博文添加对应标签关系
        if("post".equals(articles.getType())){
            articleService.addRelation(articles);
        }
        int i = articleService.updateArticle(articles);
        if(i>0){
            return APIResponse.success();
        }
        return APIResponse.fail("更新失败!");
    }
    /**
     * 删除文章
     */
    @ResponseBody
    @PostMapping("/delete")
    public APIResponse deleteArticle(Integer cid){
        int i = articleService.deleteArticleById(cid);
        if(i>0){
            return APIResponse.success();
        }
        return APIResponse.fail("删除失败!");
    }
}
