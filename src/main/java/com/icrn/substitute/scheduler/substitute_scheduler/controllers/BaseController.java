package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitutes.Controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

//@RestController
@org.springframework.stereotype.Controller
public class BaseController {


    Controller controller;
    PasswordEncoder passwordEncoder;


    public BaseController(Controller controller, PasswordEncoder passwordEncoder) {
        this.controller = controller;
        this.passwordEncoder = passwordEncoder;
    }

//    @RequestMapping("/")
//    public String index(){
//        return "Hello World";
//    }


//    @Autowired
//    UserRepository userRepository;

    @RequestMapping("/username")
    @ResponseBody
    public String currentUserName(Principal principal){
        return principal.getName();
    }


    @RequestMapping("/")
    public String index(Model model){


        return "home";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/allUsers")
    @ResponseBody
    public List<ExtUser> getAllUsers(){
        return controller.getAllUsers().stream().map(e -> (ExtUser)e).collect(Collectors.toList());
    }

    @RequestMapping(value = "/postIt",method = RequestMethod.POST)
    @ResponseBody
    public String getPoster(){
        return "something";
    }
}
