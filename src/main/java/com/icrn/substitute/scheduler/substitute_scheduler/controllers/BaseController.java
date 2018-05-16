package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.service.RequestValidator;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.Exceptions.SchedulingException;
import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.UserInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
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
        return "something else";
    }

    @RequestMapping("showAllUsers")
    public String showAllUsers(Model model){
        this.controller.getAllUsers().forEach(System.out::println);
        model.addAttribute("allUsers",this.controller.getAllUsers());
        return "allUsers";
    }

    @RequestMapping("user/{id}")
    public String showUserById(@PathVariable("id")long id, Model model, HttpServletRequest request){
        Optional<UserInterface> user = this.controller.getUserById(id);
        if(user.isPresent()){
            model.addAttribute("user",this.controller.getUserById(id).get());
            return "user";
        }
        return "error";
    }
    @RequestMapping("showAllSubstitutes")
    public String showAllSubstitutes(Model model){
        model.addAttribute("allSubstitutes",this.controller.getAllSubstitutes());

        return "allSubstitutes";
    }

    @RequestMapping("showAllRequests")
    public String showAllRequests(Model model){
        model.addAttribute("allRequests",this.controller.getAllRequests());

        return "allRequests";
    }
    @RequestMapping(value = "createRequest", method = RequestMethod.POST)
    public String createRequest(Model model,@ModelAttribute("request")Request request){
        if (!RequestValidator.validateNewRequest(request))
            throw new RuntimeException("request object is invalid");

        request = this.controller.saveRequest(request);
        model.addAttribute("request",request);
        return "request";
    }

    @PostMapping("/scheduleSubstitute")
    public String scheduleSubstitute(Model model,@ModelAttribute("request")Request request){
        if (!RequestValidator.validateUnscheduledRequest(request)) {
            System.out.println(request);
            throw new RuntimeException("request object is invalid");
        }
        try {
            request = this.controller.scheduleSubstitute(request);
            model.addAttribute("status","scheduled");
            model.addAttribute("request",request);
            return "request";

        }catch (SchedulingException e){
            model.addAttribute("error","Unable to schedule substitute");
            model.addAttribute("request",request);
            throw new RuntimeException(e);
        }

    }

//    @ModelAttribute("currentUser")
//    public void getCurrentUser(@AuthenticationPrincipal CurrentUser currentUser){
//        return; (curr)
//
////        try {
////            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////            System.out.println("T");System.out.println("T");System.out.println("T");System.out.println("T");System.out.println("T");
////            System.out.println(auth.getAuthorities());
////            System.out.println("T");System.out.println("T");System.out.println("T");System.out.println("T");System.out.println("T");
////            model.addAttribute("loggedIn",principal);
////        } catch (Exception ex) {
////
////        }
//    }
}
