package com.soul.springboot.service.impl;

import com.soul.springboot.dao.UserDao;
import com.soul.springboot.entity.User;
import com.soul.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@CacheConfig(cacheNames = "user")
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) {
        List<User> users = userDao.findAllByUser_name(username);
        if (users.size() == 0) {
            return null;
        } else {
            User user = users.get(0);
            if (user.getUser_password().equals(password)) {
                return user;
            } else {
                return new User();
            }
        }
    }

    @Override
    @Cacheable(key = "'userList'")
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Cacheable(key = "'user_id:'+#id")
    public User findById(Integer id) {
        return userDao.findById(id).get();
    }

    @Override
    @CachePut(key = "'user_id:'+#user.user_id")
    public void saveOrUpdate(User user) {
        userDao.saveAndFlush(user);
    }
}
