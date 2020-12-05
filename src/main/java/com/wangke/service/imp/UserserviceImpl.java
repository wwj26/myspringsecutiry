package com.wangke.service.imp;

import com.wangke.dao.UserDao;
import com.wangke.entity.User;
import com.wangke.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.ws.ServiceMode;
import java.util.List;

@ServiceMode
public class UserserviceImpl implements UserSevice {
    @Autowired
    private UserDao userDao;
    @Override
    public User selectUserByUserName(String username) {
        User user = new User();
        user.setUserName(username);
        List<User> list = userDao.findAll(Example.of(user));
        return list.isEmpty()?null:list.get(0);
    }
}
