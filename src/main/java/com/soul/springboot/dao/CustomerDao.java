package com.soul.springboot.dao;


import com.soul.springboot.entity.Customer;
import com.soul.springboot.entity.Dict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

    @Query(value = "select c from Customer c where c.customer_id=:customer_id")
    Customer findAllByCustomer_id(@Param("customer_id") Integer customer_id);

    @Query(value = "select c from Customer c where c.customer_name like %:name%")
    List<Customer> findAllByCustomer_nameLike(@Param("name") String customer_name);

    @Query(value = "select c from Customer c where c.customer_dict.dict_id=:dict_id")
    List<Customer> findAllByCustomer_dict_id(@Param("dict_id") Integer dict_id);

    @Query(value = "select c from Customer c where c.customer_name like %:name% and c.customer_dict.dict_id=:dict_id")
    List<Customer> findAllByCustomer_nameAndCustomer_dict_id(@Param("name") String name, @Param("dict_id") Integer dict_id);

    @Query(value = "select d from Dict d")
    List<Dict> findAllDict();

    @Query(value = "select d from Dict d where d.id=:dict")
    Dict findDictById(@Param("dict") int customer_dict_id);
}