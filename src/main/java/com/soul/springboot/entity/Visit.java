package com.soul.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "visit")
@ApiModel(value = "用户拜访客户模型")
@Data
public class Visit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    @ApiModelProperty("拜访id")
    private Integer visit_id;

    @Basic
    @Column(name = "visit_address")
    @ApiModelProperty("拜访地址")
    private String visit_address;

    @Basic
    @Column(name = "visit_content")
    @ApiModelProperty("拜访内容")
    private String visit_content;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @ApiModelProperty("拜访用户")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
    @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    @ApiModelProperty("拜访客户")
    private Customer customer;
}
