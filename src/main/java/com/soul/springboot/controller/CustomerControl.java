package com.soul.springboot.controller;

import com.soul.springboot.entity.Customer;
import com.soul.springboot.entity.PageBean;
import com.soul.springboot.service.CustomerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerControl {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取客户数据", notes = "获取客户数据", httpMethod = "GET", tags = "客户管理相关Api")
    public String list(Model model) {
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customerList", customerList);
        model.addAttribute("listDict", customerService.findAllDict());
        return "/customer/list";
    }

    @GetMapping(value = "/listPage")
    @ApiOperation(value = "获取客户分页数据", notes = "获取客户分页数据", httpMethod = "GET", tags = "客户管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页，从0开始，默认为第0页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每一页记录数的大小，默认为20", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序，格式为:property,property(,ASC|DESC)的方式组织，如sort=firstname&sort=lastname,desc", dataType = "String", paramType = "query")
    })
    public String listPage(Model model, @PageableDefault(size = 3) Pageable pageable) {
        PageBean pageBean = customerService.findAll(pageable);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("listDict", customerService.findAllDict());
        return "/customer/listPage";
    }

    @GetMapping(value = "/toAddPage")
    @ApiOperation(value = "来到客户添加页面", notes = "来到客户添加页面", httpMethod = "GET", tags = "客户管理相关Api")
    public String toAddPage(Model model) {
        model.addAttribute("listDict", customerService.findAllDict());
        return "/customer/addOrEdit";
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "添加客户数据", notes = "添加客户详情数据", httpMethod = "POST", tags = "客户管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customer", value = "客户详情数据", dataType = "Customer", paramType = "body"),
    })
    public String add(Customer customer) {
        if (customer.getCustomer_dict().getDict_id() != null) {
            customer.getCustomer_dict().setDict_name(customerService.findDictById(customer.getCustomer_dict().getDict_id()).getDict_name());
        } else {
            customer.setCustomer_dict(null);
        }
        customerService.saveOrUpdate(customer);
        return "redirect:/customer/list";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "来到客户更新页面", notes = "来到客户更新页面", httpMethod = "GET", tags = "客户管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户的主键", dataType = "int", paramType = "path")
    })
    public String showCustomer(Model model, @PathVariable(value = "id") Integer id) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("listDict", customerService.findAllDict());
        return "/customer/addOrEdit";
    }

    @PutMapping(value = "/")
    @ApiOperation(value = "更新客户数据", notes = "更新客户详情数据", httpMethod = "PUT", tags = "客户管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customer", value = "客户详情数据", dataType = "Customer", paramType = "body"),
    })
    public String edit(Customer customer) {
        if (customer.getCustomer_dict().getDict_id() != null) {
            customer.setCustomer_dict(customerService.findDictById(customer.getCustomer_dict().getDict_id()));
        } else {
            customer.setCustomer_dict(null);
        }
        customerService.saveOrUpdate(customer);
        return "redirect:/customer/list";
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除指定客户", notes = "删除指定客户", httpMethod = "DELETE", tags = "客户管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "所要删除客户的主键", dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable(value = "id") Integer id) {
        customerService.delete(id);
        return "redirect:/customer/list";
    }

    @PostMapping(value = "/find")
    @ApiOperation(value = "查找客户数据", notes = "查找客户详情数据", httpMethod = "POST", tags = "客户管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customer", value = "客户详情数据", dataType = "Customer", paramType = "body"),
    })
    public String find(Customer customer, Model model) {
        model.addAttribute("customerList", customerService.find(customer.getCustomer_name(), customer.getCustomer_dict().getDict_id()));
        model.addAttribute("listDict", customerService.findAllDict());
        return "/customer/list";
    }
}