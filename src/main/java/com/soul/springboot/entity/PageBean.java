package com.soul.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "页面模型")
@Data
public class PageBean implements Serializable {

    @ApiModelProperty("当前页")
    private Integer currentPage;

    @ApiModelProperty("总记录数")
    private long totalCount;

    @ApiModelProperty("每页显示记录数")
    private Integer pageSize;

    @ApiModelProperty("总页数")
    private Integer totalPage;

    @ApiModelProperty("每页记录的list集合")
    private List<Customer> list;
}
