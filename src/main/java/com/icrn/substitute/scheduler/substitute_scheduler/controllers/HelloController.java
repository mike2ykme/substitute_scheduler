package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitutes.model.User;
import com.icrn.substitutes.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    Controller controller;

    @RequestMapping("/")
    public String index(){
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/allUsers")
    public List<User> getAllUsers(){
        return controller.getAllUsers();
    }
}
