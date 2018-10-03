package com.soul.springboot.service;

import com.soul.springboot.entity.Linkman;

import java.util.List;

public interface LinkmanService {
    void saveOrUpdate(Linkman linkman);

    List<Linkman> findAll();

    List<Linkman> find(String linkman_name, Integer customer_id);

    Linkman findById(Integer id);

    void delete(Integer id);
}
