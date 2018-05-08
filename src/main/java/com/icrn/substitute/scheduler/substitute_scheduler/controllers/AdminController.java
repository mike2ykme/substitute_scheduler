package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.dao.ExtUserRepositoryInMemory;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.model.UserInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequestMapping("/a3n")
public class AdminController {

    Controller controller;
    PasswordEncoder passwordEncoder;

    public AdminController(Controller controller, PasswordEncoder passwordEncoder) {
        this.controller = controller;
        this.passwordEncoder = passwordEncoder;
    }


    @RequestMapping(value = "/AddUser", method = RequestMethod.POST)
    public String addUser(ExtUser user,Model model){
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        model.addAttribute("user",this.controller.saveUser(user));
        System.out.println(model.asMap().get("user"));
        return "a3n/added";
    }

   @RequestMapping(value = "DisableUser", method = RequestMethod.POST)
   public String removeUser(@RequestParam("userId") int userId,Model model){
       Optional<UserInterface> user = this.controller.getUserById(userId);
       if (user.isPresent()){
           ((ExtUser)user.get()).setActive(false);
           this.controller.saveUser(user.get());
           model.addAttribute("action","user disabled");
           model.addAttribute("userId",Long.toString(user.get().getId()));

           return "a3n/disabled";
       }
        model.addAttribute("error","Unable to find user with id");
        return "error";

   }

    @RequestMapping("test")
    public String test(){
        return "test";
    }
    @RequestMapping(value = "/")
    public String main(){
        return "test";
    }


    // https://stackoverflow.com/questions/25625056/how-to-obtain-csrf-token-in-a-velocity-macro-when-using-spring-security/25639809
    @ResponseBody
    @RequestMapping("/csrf")
    public CsrfToken token(HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return token;
    }
}
