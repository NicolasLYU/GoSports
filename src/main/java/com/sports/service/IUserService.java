package com.sports.service;

import com.sports.model.UserVO;

/**
 * Created by Gundam on 2016/10/25.
 */
public interface IUserService {
    boolean isExist(String loginName);

    UserVO addUser(String loginName, String password);

    UserVO findUserByAccount(String loginName);

    void updateToken(UserVO user);

    boolean register(UserVO user);
}