package com.soul.springboot.service.impl;

import com.soul.springboot.dao.LinkmanDao;
import com.soul.springboot.entity.Linkman;
import com.soul.springboot.service.LinkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@CacheConfig(cacheNames = "linkman")
@Service
@Transactional
public class LinkmanServiceImpl implements LinkmanService {

    @Autowired
    private LinkmanDao linkmanDao;

    @Override
    @Caching(
            put = {
                    @CachePut(key = "'linkman_id:'+#linkman.linkman_id")
            },
            evict = {
                    @CacheEvict(key = "'linkmanList'")
            }
    )
    public void saveOrUpdate(Linkman linkman) {
        linkmanDao.saveAndFlush(linkman);
    }

    @Override
    @Cacheable(key = "'linkmanList'")
    public List<Linkman> findAll() {
        return linkmanDao.findAll();
    }

    @Override
    public List<Linkman> find(String linkman_name, Integer customer_id) {
        if (customer_id == null && linkman_name == "") {
            return linkmanDao.findAll();
        } else if (customer_id == null) {
            return linkmanDao.findAllByLinkman_nameLike(linkman_name);
        } else if (linkman_name == "") {
            return linkmanDao.findAllByCustomer_id(customer_id);
        } else {
            return linkmanDao.findAllByLinkman_nameAndCustomer_id(linkman_name, customer_id);
        }
    }

    @Override
    @Cacheable(key = "'linkman_id:'+#id")
    public Linkman findById(Integer id) {
        return linkmanDao.findAllByLinkman_id(id);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "'linkman_id:'+#id"),
                    @CacheEvict(key = "'linkmanList'")
            }
    )
    public void delete(Integer id) {
        linkmanDao.deleteById(id);
    }
}
