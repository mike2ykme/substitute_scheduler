package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.DateWrapper;
import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.Domain.SubWrapper;
import com.icrn.substitute.scheduler.substitute_scheduler.configuration.UserDetailsServiceRepo;
import com.icrn.substitute.scheduler.substitute_scheduler.service.RequestValidator;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.Exceptions.SchedulingException;
import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.Substitute;
import com.icrn.substitutes.model.UserInterface;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@RestController
@org.springframework.stereotype.Controller
public class BaseController {


    Controller controller;
    PasswordEncoder passwordEncoder;
    UserDetailsServiceRepo userDetailsServiceRepo;


    public BaseController(Controller controller, PasswordEncoder passwordEncoder, UserDetailsServiceRepo userDetailsServiceRepo) {
        this.controller = controller;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsServiceRepo = userDetailsServiceRepo;
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
            ExtUser user1 = (ExtUser) user.get();
            user1.setPassword("");
            model.addAttribute("user",user1);
            return "user";
        }
        return "error";
    }
    @RequestMapping("showAllSubstitutes")
    public String showAllSubstitutes(Model model){
        model.addAttribute("allSubstitutes",this.controller.getAllSubstitutes());

        return "allSubstitutes";
    }
    @RequestMapping("substitute/{id}")
    public String showSubstituteById(@PathVariable("id")long id, Model model, HttpServletRequest request){
        Optional<Substitute> optSub = this.controller.getSubstituteById(id);
        if (optSub.isPresent()){
            Substitute substitute = optSub.get();
            SubWrapper wrapper = SubWrapper.getWrapperFromSubstitute(substitute);
            model.addAttribute("substitute",wrapper);
            System.out.println("SUBSTITUTE");
            System.out.println(substitute);
            System.out.println("WRAPPER");
            System.out.println(wrapper);

            return "substitute";
        }
//        model.addAttribute("allSubstitutes",this.controller.getAllSubstitutes());

        return "redirect:error";
    }

    @RequestMapping("showAllRequests")
    public String showAllRequests(Model model){
        model.addAttribute("allRequests",this.controller.getAllRequests());

        return "allRequests";
    }
    @RequestMapping("requestSelectSubstitute/{id}")
    public String selectUserForRequest(Model model, DateWrapper dateWrapper, @PathVariable("id")long id, @AuthenticationPrincipal UserDetails currentUser){
        Optional<Substitute> optSub = this.controller.getSubstituteById(id);
        if (optSub.isPresent()){
            System.out.println("INSIDE requestSelectSubstitute/{id}");
            Request request1 = new Request();
            request1.setStartTime(LocalDateTime.of(dateWrapper.getStartDay(),dateWrapper.getStartTime()));
            request1.setEndTime(LocalDateTime.of(dateWrapper.getStartDay(),dateWrapper.getEndTime()));
            request1.setSubstituteId(optSub.get().getId());
            ExtUser user = (ExtUser) this.userDetailsServiceRepo.loadUserByUsername(currentUser.getUsername());
            System.out.println(user);
            request1.setRequestorId(user.getId());
            try{
                Request requestScheduled = this.controller.scheduleSubstitute(request1);
                System.out.println(requestScheduled);
                model.addAttribute("request",requestScheduled);
                model.addAttribute("substitute",this.controller.getSubstituteById(requestScheduled.getSubstituteId()).get());
            }catch (SchedulingException e){
                System.out.println(e);
                throw new RuntimeException(e);
            }

            return "substituteScheduled";
        }
        return "error";
    }

    @RequestMapping(value = "newRequestStart")
    public String getRequestForm(Model model){
        model.addAttribute("time", DateWrapper.dateWrapperNow());
        return "requestStart";
    }

    @RequestMapping(value = "requestAvailableSubstitutes", method =RequestMethod.POST)
    public String getAvailableSubstitutes(Model model, DateWrapper dateWrapper){
        System.out.println(dateWrapper);
        LocalDateTime start = LocalDateTime.of(dateWrapper.getStartDay(),dateWrapper.getStartTime());
        LocalDateTime end = LocalDateTime.of(dateWrapper.getStartDay(),dateWrapper.getEndTime());
        System.out.println(this.controller.getSubstitutesAvailableOnDateTime(start,end));
        model.addAttribute("allSubstitutes",this.controller.getSubstitutesAvailableOnDateTime(start,end));
        model.addAttribute("selectedDate",dateWrapper);
        return "requestAvailableSubstitutes";
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
