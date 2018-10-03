package com.soul.springboot.service;

import com.soul.springboot.entity.User;

import java.util.List;

public interface UserService {
    User login(String username, String password);

    List<User> findAll();

    User findById(Integer id);

    void saveOrUpdate(User user);
}
