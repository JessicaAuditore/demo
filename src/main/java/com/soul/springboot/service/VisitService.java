package com.soul.springboot.service;

import com.soul.springboot.entity.Visit;

import java.util.List;

public interface VisitService {
    List<Visit> findAll();

    void saveOrUpdate(Visit visit);

    void delete(Integer id);

    Visit findById(Integer id);

    List<Visit> find(Integer customer_id, Integer user_id);
}
