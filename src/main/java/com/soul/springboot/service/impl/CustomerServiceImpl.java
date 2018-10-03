package com.soul.springboot.service.impl;

import com.soul.springboot.dao.CustomerDao;
import com.soul.springboot.entity.Customer;
import com.soul.springboot.entity.Dict;
import com.soul.springboot.entity.PageBean;
import com.soul.springboot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "customer")
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(key = "'customerList'")
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    @Cacheable(key = "'customerList_pageNumber:'+#pageable.pageNumber")
    public PageBean findAll(Pageable pageable) {
        Page<Customer> page = customerDao.findAll(pageable);
        PageBean pageBean = new PageBean();
        if (null != page) {
            pageBean.setList(page.getContent().stream().map((customer) -> {
                return new Customer(customer);
            }).collect(Collectors.toList()));
            pageBean.setTotalCount(page.getTotalElements());
            pageBean.setTotalPage(page.getTotalPages());
            pageBean.setPageSize(pageable.getPageSize());
            pageBean.setCurrentPage(pageable.getPageNumber());
            return pageBean;
        }
        return null;
    }

    @Override
    @Cacheable(key = "'customer_id:'+#id")
    public Customer findById(Integer id) {
        return customerDao.findAllByCustomer_id(id);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(key = "'customer_id:'+#customer.customer_id")
            },
            evict = {
                    @CacheEvict(key = "'customerList'")
            }
    )
    public Customer saveOrUpdate(Customer customer) {
        customerDao.saveAndFlush(customer);

        Cache cache = cacheManager.getCache("customer");
        int i = 0;
        while (cache.get("customerList_pageNumber:" + i) != null) {
            cache.evict("customerList_pageNumber:" + i);
            i++;
        }
        return customer;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "'customer_id:'+#id"),
                    @CacheEvict(key = "'customerList'")
            }
    )
    public void delete(Integer id) {
        customerDao.deleteById(id);

        Cache cache = cacheManager.getCache("customer");
        int i = 0;
        while (cache.get("customerList_pageNumber:" + i) != null) {
            cache.evict("customerList_pageNumber:" + i);
            i++;
        }
    }

    @Override
    public List<Customer> find(String name, Integer dict_id) {
        if (dict_id == null && name == "") {
            return customerDao.findAll();
        } else if (dict_id == null) {
            return customerDao.findAllByCustomer_nameLike(name);
        } else if (name == "") {
            return customerDao.findAllByCustomer_dict_id(dict_id);
        } else {
            return customerDao.findAllByCustomer_nameAndCustomer_dict_id(name, dict_id);
        }
    }

    @Override
    @Cacheable(key = "'dictList'")
    public List<Dict> findAllDict() {
        return customerDao.findAllDict();
    }

    @Override
    @Cacheable(key = "'dict_id:'+#customer_dict_id")
    public Dict findDictById(int customer_dict_id) {
        return customerDao.findDictById(customer_dict_id);
    }
}
