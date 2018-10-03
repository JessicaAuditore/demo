package com.soul.springboot.dao;

import com.soul.springboot.entity.Linkman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkmanDao extends JpaRepository<Linkman, Integer> {

    @Query(value = "select l from Linkman l where l.linkman_id=:linkman_id")
    Linkman findAllByLinkman_id(@Param("linkman_id") Integer linkman_id);

    @Query(value = "select l from Linkman l where l.linkman_name like %:name%")
    List<Linkman> findAllByLinkman_nameLike(@Param("name") String name);

    @Query(value = "select l from Linkman l where l.customer.id = :customer_id")
    List<Linkman> findAllByCustomer_id(@Param("customer_id") Integer customer_id);

    @Query(value = "select l from Linkman l where l.linkman_name like %:name% and l.customer.id = :customer_id")
    List<Linkman> findAllByLinkman_nameAndCustomer_id(@Param("name") String name, @Param("customer_id") Integer customer_id);
}
