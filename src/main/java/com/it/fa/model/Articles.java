package com.it.fa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 文章类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articles implements Serializable {
    /**
     * 文章主键
     */
    private Integer cid;
    /**
     * 文章创建时间 UNIX时间戳
     */
    private Integer created;
    /**
     * 文章更新时间
     */
    private Integer modified;
    /**
     * 文章标题
     */
    private String  title;
    /**
     * 文章类型 博文?作品
     */
    private String type;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章标签
     */
    private String tags;
    /**
     * 文章范畴 天文？地理？军事？
     */
    private String categories;
    /**
     * 文章状态发布/未发布
     */
    private String status;
    /**
     * 文章评论数
     */
    private String  commentsNum;
    /**
     * 文章是否允许评论
     */
    private String allowComment;
    /**
     * 阅读量 Redis实现
     */
    private String reads;
    /**
     * 文章对应的标签集
     */
    private List<Label> labels;

}
