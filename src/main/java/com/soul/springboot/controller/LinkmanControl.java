package com.soul.springboot.controller;

import com.soul.springboot.entity.Linkman;
import com.soul.springboot.service.CustomerService;
import com.soul.springboot.service.LinkmanService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/linkman")
public class LinkmanControl {

    @Autowired
    private LinkmanService linkmanService;

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取联系人数据", notes = "获取联系人数据", httpMethod = "GET", tags = "联系人管理相关Api")
    public String list(Model model) {
        model.addAttribute("linkmanList", linkmanService.findAll());
        model.addAttribute("customerList", customerService.findAll());
        return "/linkman/list";
    }

    @GetMapping(value = "/toAddPage")
    @ApiOperation(value = "来到联系人添加页面", notes = "来到联系人添加页面", httpMethod = "GET", tags = "联系人管理相关Api")
    public String toAddPage(Model model) {
        model.addAttribute("customerList", customerService.findAll());
        return "/linkman/addOrEdit";
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "添加联系人数据", notes = "添加联系人详情数据", httpMethod = "POST", tags = "联系人管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkman", value = "联系人详情数据", dataType = "Linkman", paramType = "body"),
    })
    public String add(Linkman linkman) {
        if (linkman.getCustomer().getCustomer_id() != null) {
            linkman.getCustomer().setCustomer_name(customerService.findById(linkman.getCustomer().getCustomer_id()).getCustomer_name());
        } else {
            linkman.setCustomer(null);
        }
        linkmanService.saveOrUpdate(linkman);
        return "redirect:/linkman/list";
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "来到联系人更新页面", notes = "来到联系人更新页面", httpMethod = "GET", tags = "联系人管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "联系人的主键", dataType = "int", paramType = "path")
    })
    public String showLinkman(Model model, @PathVariable(value = "id") Integer id) {
        model.addAttribute("linkman", linkmanService.findById(id));
        model.addAttribute("customerList", customerService.findAll());
        return "/linkman/addOrEdit";
    }

    @PutMapping(value = "/")
    @ApiOperation(value = "更新联系人数据", notes = "更新联系人详情数据", httpMethod = "PUT", tags = "联系人管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkman", value = "联系人详情数据", dataType = "Linkman", paramType = "body"),
    })
    public String edit(Linkman linkman) {
        if (linkman.getCustomer().getCustomer_id() != null) {
            linkman.setCustomer(customerService.findById(linkman.getCustomer().getCustomer_id()));
        } else {
            linkman.setCustomer(null);
        }
        linkmanService.saveOrUpdate(linkman);
        return "redirect:/linkman/list";
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除指定联系人", notes = "删除指定联系人", httpMethod = "DELETE", tags = "联系人管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "所要删除联系人的主键", dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable(value = "id") Integer id) {
        linkmanService.delete(id);
        return "redirect:/linkman/list";
    }

    @PostMapping(value = "/find")
    @ApiOperation(value = "查找联系人数据", notes = "查找联系人详情数据", httpMethod = "POST", tags = "联系人管理相关Api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "linkman", value = "联系人详情数据", dataType = "Linkman", paramType = "body"),
    })
    public String find(Linkman linkman, Model model) {
        model.addAttribute("linkmanList", linkmanService.find(linkman.getLinkman_name(), linkman.getCustomer().getCustomer_id()));
        model.addAttribute("customerList", customerService.findAll());
        return "/linkman/list";
    }
}
