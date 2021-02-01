package com.it.fa.service.article;

import com.github.pagehelper.PageInfo;
import com.it.fa.model.Articles;
import com.it.fa.model.Info;

import java.util.List;

public interface IArticleService {
    PageInfo<Articles> findAll(int page,int limit);

    Articles findArticleById(Integer cid);

    int deleteArticleById(Integer cid);

    List<Articles> findLately();

    int addArticles(Articles articles);

    PageInfo<Articles> findAllByType(int page, int limit, String type);

    void addRelation(Articles articles);

    int updateArticle(Articles articles);

    Info findArticleBaseInfo();
}
