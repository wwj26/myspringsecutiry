package com.wangke.service;

import com.wangke.entity.User;

public interface UserSevice {
    public User selectUserByUserName(String username);
}