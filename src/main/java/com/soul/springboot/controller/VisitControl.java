package com.soul.springboot.controller;

import com.soul.springboot.entity.Visit;
import com.soul.springboot.service.CustomerService;
import com.soul.springboot.service.UserService;
import com.soul.springboot.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/visit")
public class VisitControl {

    @Autowired
    private VisitService visitService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", visitService.findAll());
        mv.addObject("listCustomer", customerService.findAll());
        mv.addObject("listUser", userService.findAll());
        mv.setViewName("/visit/list");
        return mv;
    }

    @RequestMapping("/toAddPage")
    public ModelAndView toAddPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("listCustomer", customerService.findAll());
        mv.addObject("listUser", userService.findAll());
        mv.setViewName("/visit/add");
        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView add(Visit visit) {
        if (visit.getCustomer().getCustomer_id() != null) {
            visit.getCustomer().setCustomer_name(customerService.findById(visit.getCustomer().getCustomer_id()).getCustomer_name());
        } else {
            visit.setCustomer(null);
        }
        if (visit.getUser().getUser_id() != null) {
            visit.getUser().setUser_realname(userService.findById(visit.getUser().getUser_id()).getUser_realname());
        } else {
            visit.setUser(null);
        }
        visitService.saveOrUpdate(visit);
        return list();
    }

    @RequestMapping("/delete")
    public ModelAndView delete(Integer id) {
        visitService.delete(id);
        return list();
    }

    @RequestMapping("/showVisit")
    public ModelAndView showVisit(Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("visit", visitService.findById(id));
        mv.addObject("listCustomer", customerService.findAll());
        mv.addObject("listUser", userService.findAll());
        mv.setViewName("/visit/edit");
        return mv;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(Visit visit) {
        visitService.saveOrUpdate(visit);
        return list();
    }

    @RequestMapping("/find")
    public ModelAndView find(Visit visit) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", visitService.find(visit.getCustomer().getCustomer_id(), visit.getUser().getUser_id()));
        mv.addObject("listCustomer", customerService.findAll());
        mv.addObject("listUser", userService.findAll());
        mv.setViewName("/visit/list");
        return mv;
    }
}
