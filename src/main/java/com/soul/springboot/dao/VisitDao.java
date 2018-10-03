package com.soul.springboot.dao;

import com.soul.springboot.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitDao extends JpaRepository<Visit, Integer> {

    @Query(value = "select v from Visit v where v.visit_id=:visit_id")
    Visit findAllByVisit_id(@Param("visit_id") Integer visit_id);

    @Query(value = "select v from Visit v where v.customer.id = :customer_id")
    List<Visit> findAllByCustomer_id(@Param("customer_id") Integer customer_id);

    @Query(value = "select v from Visit v where v.user.id = :user_id")
    List<Visit> findAllByUser_id(@Param("user_id") Integer user_id);

    @Query(value = "select v from Visit v where v.customer.id = :customer_id and v.user.id = :user_id")
    List<Visit> findAllByCustomer_idAndUser_id(@Param("customer_id") Integer customer_id, @Param("user_id") Integer user_id);
}
