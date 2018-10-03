package com.soul.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@ApiModel(value = "用户模型")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    @ApiModelProperty("用户id")
    private Integer user_id;

    @Basic
    @Column(name = "user_name")
    @ApiModelProperty("用户名称")
    private String user_name;

    @Basic
    @Column(name = "user_password")
    @ApiModelProperty("用户密码")
    private String user_password;

    @Basic
    @Column(name = "user_realname")
    @ApiModelProperty("用户真实姓名")
    private String user_realname;

    @Basic
    @Column(name = "user_address")
    @ApiModelProperty("用户地址")
    private String user_address;

    @Basic
    @Column(name = "user_level")
    @ApiModelProperty("用户等级")
    private String user_level;
}
