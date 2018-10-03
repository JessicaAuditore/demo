package com.soul.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "customer")
@ApiModel(value = "客户模型")
@Data
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @ApiModelProperty("客户id")
    private Integer customer_id;

    @Basic
    @Column(name = "customer_name")
    @ApiModelProperty("客户名称")
    private String customer_name;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Dict.class)
    @JoinColumn(name = "customer_dict_id")
    @ApiModelProperty("客户级别")
    private Dict customer_dict;

    @Basic
    @Column(name = "customer_address")
    @ApiModelProperty("客户地址")
    private String customer_address;

    @Basic
    @Column(name = "customer_telephone")
    @ApiModelProperty("客户电话")
    private String customer_telephone;

    @Basic
    @Column(name = "customer_source")
    @ApiModelProperty("客户来源")
    private String customer_source;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY, targetEntity = Linkman.class)
    private transient Set<Linkman> linkManSet;

    public Customer(Customer customer){
        this.customer_id=customer.getCustomer_id();
        this.customer_address=customer.getCustomer_address();
        this.customer_dict=customer.getCustomer_dict();
        this.customer_name=customer.getCustomer_name();
        this.customer_source=customer.getCustomer_source();
        this.customer_telephone=customer.getCustomer_telephone();
        this.linkManSet=customer.getLinkManSet();
    }

    public Customer(){

    }
}
