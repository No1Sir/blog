package com.it.fa.service.user;

import com.it.fa.model.User;

public interface IUserService {
    User login(String username, String password);

    int update(User u);

    int updatePwd(User u, String oldPassword, String password);
}
