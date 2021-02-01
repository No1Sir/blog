package com.it.fa.service.user.imp;

import com.it.fa.constant.ErrorConstant;
import com.it.fa.dao.IUserDao;
import com.it.fa.exception.DefineException;
import com.it.fa.exception.DefineException;
import com.it.fa.model.User;
import com.it.fa.service.user.IUserService;
import com.it.fa.utils.Md5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;

    @Override
    public User login(String username, String password) {
        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            throw DefineException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);
        }
        String pwd = Md5.MD5encode(username+password);
        User u = userDao.login(username,pwd);
        if(null == u){
            throw DefineException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);
        }
        return u;
    }
    @Override
    public int update(User u) {
        return userDao.update(u);
    }

    @Override
    public int updatePwd(User u, String oldPassword, String password) {

        if(Md5.MD5encode(u.getUsername()+oldPassword).equals(u.getPassword())){
           return userDao.updatePwd(u.getUid(),Md5.MD5encode(u.getUsername()+password));
        }
        return 0;
    }
}
