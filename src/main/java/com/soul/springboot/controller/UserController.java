package com.soul.springboot.controller;

import com.soul.springboot.entity.User;
import com.soul.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String login() {
        return "user/login";
    }

    @PostMapping(value = "/loginHandle")
    public String loginHandle(@RequestParam("user_name") String user_name, @RequestParam("user_password") String user_password, @RequestParam(value = "remember_me", required = false) String remember_me, HttpSession session, Map<String, Object> map) {
        if (remember_me != null) {
            session.setAttribute("name", user_name);
        } else {
            session.removeAttribute("name");
        }

        User user = userService.login(user_name, user_password);
        if (user == null) {
            map.put("msg", "用户不存在!");
            session.removeAttribute("name");
            return "user/login";
        } else if (user.getUser_password() == null) {
            map.put("msg", "密码错误!");
            return "user/login";
        } else {
            session.setAttribute("user", user);
            return "redirect:/index.html";
        }
    }

    @GetMapping(value = "/exit")
    public String exit(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("msg");
        return "user/login";
    }


    @GetMapping(value = "/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping(value = "/top")
    public String top() {
        return "top";
    }

    @GetMapping(value = "/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping(value = "/noRight")
    public String noRight() {
        return "state/noRight";
    }

    @GetMapping(value = "/toModifyPage")
    public String toModifyPage() {
        return "user/modify";
    }

    @PostMapping(value = "/modify")
    public String modify(HttpServletRequest request, String oldPassword, String newPassword, String repeatPassword, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getUser_password().equals(oldPassword)) {
            model.addAttribute("error", "原密码错误");
            return "state/error";
        }
        if (!newPassword.equals(repeatPassword)) {
            model.addAttribute("error", "两次输入密码不同");
            return "state/error";
        }
        if (oldPassword.equals(newPassword)) {
            model.addAttribute("error", "新密码与原密码相同");
            return "state/error";
        }
        user.setUser_password(newPassword);
        userService.saveOrUpdate(user);
        request.getSession().setAttribute("user", user);
        return "state/success";
    }
}
