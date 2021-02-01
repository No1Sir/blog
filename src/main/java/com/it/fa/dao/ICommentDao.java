package com.it.fa.dao;

import com.it.fa.model.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ICommentDao {
    @Select("select * from t_comments order by created desc limit 0,5")
    List<Comment> findLately();
    @Select("select * from t_comments")
    List<Comment> findAll();
    @Select("select * from t_comments where cid = #{cid} and status = 'approved'")
    List<Comment> findAllByCid(Integer cid);
}
