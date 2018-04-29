package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitutes.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
@RequestMapping("/a3n")
public class AdminController {

    Controller controller;

    public AdminController(Controller controller) {
        this.controller = controller;
    }

//    @RequestMapping(value = "/users", method = RequestMethod.POST)
//    public String addUser(Model model){
//        return "dir/test";
//    }

    @RequestMapping(value = "/AddUser", method = RequestMethod.POST)
    public String addUser(ExtUser user,Model model){
        System.out.println(user);
        model.addAttribute("user",this.controller.saveUser(user));
        System.out.println(model.asMap().get("user"));
        return "dir/test";
    }

    @RequestMapping("test")
    public String test(){
        return "test";
    }
    @RequestMapping(value = "/")
    public String main(){
        return "test";
    }
}
