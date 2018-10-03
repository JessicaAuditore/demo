package com.soul.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "linkman")
@ApiModel(value = "联系人模型")
@Data
public class Linkman implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "linkman_id")
    @ApiModelProperty("联系人id")
    private Integer linkman_id;

    @Basic
    @Column(name = "linkman_name")
    @ApiModelProperty("联系人名称")
    private String linkman_name;

    @Basic
    @Column(name = "linkman_gender")
    @ApiModelProperty("联系人性别")
    private String linkman_gender;

    @Basic
    @Column(name = "linkman_address")
    @ApiModelProperty("联系人地址")
    private String linkman_address;

    @Basic
    @Column(name = "linkman_telephone")
    @ApiModelProperty("联系人电话")
    private String linkman_telephone;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ApiModelProperty("联系人所属顾客")
    private Customer customer;
}
