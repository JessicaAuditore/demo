package com.soul.springboot.dao;

import com.soul.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    @Query(value = "select u from User u where u.user_name=:user_name")
    List<User> findAllByUser_name(@Param("user_name") String user_name);
}
