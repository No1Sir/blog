package com.it.fa.service.article.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.fa.dao.IArticleDao;
import com.it.fa.model.Articles;
import com.it.fa.model.Info;
import com.it.fa.service.article.IArticleService;
import com.it.fa.service.label.ILabelService;
import com.it.fa.service.relation.IRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class IArticleServiceImpl implements IArticleService {
    @Resource
    private IArticleDao articleDao;
    @Resource
    private IRelationService relationService;
    @Resource
    private ILabelService labelService;
    //根据名称注入
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    @CachePut(value = "articles",key = "'allArticles'")
    public PageInfo<Articles> findAll(int page, int limit) {
        PageHelper.startPage(page,limit);
        List<Articles> articles = articleDao.findAll();
        PageInfo<Articles> p = new PageInfo(articles);
        return p;
    }

    @Override
    public Articles findArticleById(Integer cid) {
        return articleDao.findArticleById(cid);
    }

    @Override
    public int deleteArticleById(Integer cid) {
        return articleDao.deleteArticleById(cid);
    }
    @CacheEvict(value = "articles",key = "'latelyArticles'",allEntries = true,beforeInvocation = true)
    @Override
    public List<Articles> findLately() {
        return articleDao.findLately();
    }

    @Override
    public int addArticles(Articles articles) {
        return articleDao.addArticles(articles);
    }

    @Override
    public PageInfo<Articles> findAllByType(int page, int limit, String type) {
        PageHelper.startPage(page,limit);
        List<Articles> articles = articleDao.findAllByType(type);
        PageInfo<Articles> p = new PageInfo(articles);
        return p;
    }

    @Override
    public void addRelation(Articles articles) {
       HashMap<String,Integer> map = new HashMap<>();
       int length = 0;
       //获取请求中的标签
       if(!StringUtils.isBlank(articles.getCategories()))
       {
           for(String t:articles.getCategories().split(",")){
               map.put(t,1);
               length++;
           }
       }
       if(!StringUtils.isBlank(articles.getTags())){
            for(String t:articles.getTags().split(",")){
                map.put(t,1);
                length++;
            }
       }
       //查找出数据库中的标签
       String[] oldMetas = labelService.findAllInRequest(articles.getCid(),map);
       //比较两者,为不存在数据库中的新标签添加标识
       for(String t:oldMetas) {
           for (Map.Entry<String, Integer> entry : map.entrySet()) {
               if (entry.getKey().equals(t)) {
                   map.put(entry.getKey(), 2);
               }
           }
       }
       //获取新标签
       List<String> newNames = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue()!=2) {
                newNames.add(entry.getKey());
            }
        }
       //注意 如果库中已经有该标签 就不用再加入库中
       int len = labelService.findAllInRequestLength(map);
       //添加新标签到数据库
       if(len!=length) {
            for (String t : articles.getTags().split(",")) {
                for (String k : newNames) {
                    if (t.equals(k)) {
                        labelService.addTag(t);
                    }
                }
            }
            for (String t : articles.getCategories().split(",")) {
                for (String k : newNames) {
                    if (t.equals(k)) {
                        labelService.addCategory(t);
                    }
                }
            }
        }
       //有新标签 添加新的关系
       if(newNames.size()!=0){
           List<Integer> newLabels = labelService.findAllByNames(newNames);
           relationService.addRelation(articles.getCid(),newLabels);
       }
       //否则关系不变或减少
       else{
           List<Integer> list = labelService.findAllOldMetas(articles.getCid(),oldMetas);
           //size为0表示没有标签变化
           if(list.size()!=0){
               relationService.deleteRelation(articles.getCid(),list);
           }
       }

    }

    @Override
    public int updateArticle(Articles articles) {
        return articleDao.updateArticle(articles);
    }

    @Override
    @Cacheable(value = "articles",key = "'baseInfo'")
    public Info findArticleBaseInfo() {
        return articleDao.findArticleBaseInfo();
    }
    @Override
    public String getReads(Integer cid) {
        //查redis
        String r2 = stringRedisTemplate.opsForValue().get("comment_" + cid);
        if(null!=r2){
            System.out.println("redis中的数据");
            Long reads = stringRedisTemplate.opsForValue().increment("comment_"+cid, 1);
            return reads.toString();
        }
        //查数据库
        String reads = articleDao.findReads(cid);
        int k = Integer.valueOf(reads)+1;
        stringRedisTemplate.opsForValue().set("comment_"+cid,String.valueOf(k));
        return reads;
    }
}
