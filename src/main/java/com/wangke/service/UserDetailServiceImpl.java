package com.wangke.service;

import com.wangke.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserSevice userSevice;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userSevice.selectUserByUserName(s);
        if(user==null){
            throw new UsernameNotFoundException("登录用户："+s+"不存在");
        }
        user.setAuthorityList(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
        return user;
    }
}
