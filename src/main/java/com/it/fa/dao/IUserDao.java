package com.it.fa.dao;

import com.it.fa.model.User;
import org.apache.ibatis.annotations.Param;

public interface IUserDao {
    User login(@Param("username") String username,@Param("password") String password);

    int update(@Param("user") User u);

    int updatePwd(@Param("uid") Integer uid, @Param("new") String password);
}
