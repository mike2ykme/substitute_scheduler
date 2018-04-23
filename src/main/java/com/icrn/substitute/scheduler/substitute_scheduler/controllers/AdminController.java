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

    @RequestMapping("/users")
    public String addUser(ExtUser user, Model model){
        return "dir/test";
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String main(){
        return "test";
    }
}
