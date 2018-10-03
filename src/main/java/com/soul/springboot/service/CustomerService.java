package com.soul.springboot.service;

import com.soul.springboot.entity.Customer;
import com.soul.springboot.entity.Dict;
import com.soul.springboot.entity.PageBean;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    PageBean findAll(Pageable pageable);

    List<Customer> findAll();

    Customer findById(Integer id);

    Customer saveOrUpdate(Customer customer);

    void delete(Integer id);

    List<Customer> find(String name, Integer dict_id);

    List<Dict> findAllDict();

    Dict findDictById(int customer_dict_id);
}
