package com.soul.springboot.service.impl;

import com.soul.springboot.dao.VisitDao;
import com.soul.springboot.entity.Visit;
import com.soul.springboot.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitDao visitDao;

    @Override
    @Cacheable(key = "'visitList'")
    public List<Visit> findAll() {
        return visitDao.findAll();
    }

    @Override
    @CachePut(key = "'visit_id:'+#visit.visit_id")
    public void saveOrUpdate(Visit visit) {
        visitDao.saveAndFlush(visit);
    }

    @Override
    @CacheEvict(key = "'visit_id:'+#id")
    public void delete(Integer id) {
        visitDao.deleteById(id);
    }

    @Override
    @Cacheable(key = "'visit_id:'+#id")
    public Visit findById(Integer id) {
        return visitDao.findAllByVisit_id(id);
    }

    @Override
    public List<Visit> find(Integer customer_id, Integer user_id) {
        if (customer_id == null && user_id == null) {
            return visitDao.findAll();
        } else if (customer_id == null) {
            return visitDao.findAllByUser_id(user_id);
        } else if (user_id == null) {
            return visitDao.findAllByCustomer_id(customer_id);
        } else {
            return visitDao.findAllByCustomer_idAndUser_id(customer_id, user_id);
        }
    }
}
