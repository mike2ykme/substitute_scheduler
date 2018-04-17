package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.dao.UserRepository;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

//@RestController
@org.springframework.stereotype.Controller
public class BaseController {


    Controller controller;


    public BaseController(Controller controller) {
        this.controller = controller;
    }

//    @RequestMapping("/")
//    public String index(){
//        return "Hello World";
//    }
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/username")
    @ResponseBody
    public String currentUserName(Principal principal){
        return principal.getName();
    }


    @RequestMapping("/")
    public String index(Model model){

        User user = new User();
        user.setName("Micheal");
        user.setId(000000000000000001L);

        userRepository.save(user);
        userRepository.findAll().stream().forEach(System.out::println);


        System.out.println("password");
        System.out.println(passwordEncoder.encode("password"));
        System.out.println("admin");
        System.out.println(passwordEncoder.encode("admin"));



        controller.saveUser(user);
        model.addAttribute("Users",controller.getAllUsers());
        model.addAttribute("UserList",new ArrayList<User>());
        model.addAttribute("me",user);
        return "home";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
    @RequestMapping("/allUsers")
    public List<User> getAllUsers(){
        return controller.getAllUsers();
    }
}
