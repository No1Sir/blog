package com.it.fa.dao;

import com.it.fa.model.Articles;
import com.it.fa.model.Info;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import java.util.List;

public interface IArticleDao {

    List<Articles> findAll();

    List<Articles> findAllByType(@Param("type") String type);

    Articles findArticleById(Integer cid);

    int deleteArticleById(Integer cid);

    List<Articles> findLately();

    int addArticles(Articles articles);

    int updateArticle(Articles articles);
    @Select("select a.comment_count,b.attach_count,c.content_count from"+
            "("+
            "  (select count(*) comment_count from t_comments)a,"+
            "  (select count(*) attach_count from t_attach)b,"+
            "  (select count(*) content_count from t_contents where status = 'publish')c"+
            ")")
    Info findArticleBaseInfo();
    @Select("select t_contents.reads from t_contents where cid = #{cid}")
    String findReads(Integer cid);
}
