package com.soul.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dict")
@ApiModel(value = "用户等级模型")
@Data
public class Dict implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dict_id")
    @ApiModelProperty("客户等级id")
    private Integer dict_id;

    @Basic
    @Column(name = "dict_name")
    @ApiModelProperty("客户等级名称")
    private String dict_name;
}
