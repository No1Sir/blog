package com.it.fa.service.comment;

import com.github.pagehelper.PageInfo;
import com.it.fa.model.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> findLately();

    PageInfo<Comment> findAll(int page, int limit);

    List<Comment> findAllByCid(Integer cid);
}
