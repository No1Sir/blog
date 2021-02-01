package com.it.fa.service.comment.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.fa.dao.ICommentDao;
import com.it.fa.model.Comment;
import com.it.fa.service.comment.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Resource
    private ICommentDao commentDao;

    @Override
    public List<Comment> findLately() {
        return commentDao.findLately();
    }

    @Override
    public PageInfo<Comment> findAll(int page, int limit) {
        PageHelper.startPage(page,limit);
        List<Comment> list = commentDao.findAll();
        PageInfo<Comment> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public List<Comment> findAllByCid(Integer cid) {
        return commentDao.findAllByCid(cid);
    }
}
