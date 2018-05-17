package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.dao.ExtUserRepositoryInMemory;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.model.UserInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addUser(ExtUser user, Model model) {
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        model.addAttribute("user", this.controller.saveUser(user));
        System.out.println(model.asMap().get("user"));
        return "a3n/added";
    }

    @RequestMapping(value = "/SaveUser", method = RequestMethod.POST)
    public String saveUser(ExtUser user, Model model,RedirectAttributes redirectAttrs) {
        Optional<UserInterface> optUser = this.controller.getUserById(user.getId());

        if (user.getId() != 0 && optUser.isPresent()) {
            ExtUser userFromDb = (ExtUser) optUser.get();

            if (user.getPassword() != null && user.getPassword() != "")
                userFromDb.setPassword(this.passwordEncoder.encode(user.getPassword()));

            if (!user.getName().equals(userFromDb.getName()))
                userFromDb.setName(user.getName());

            if (!user.getContactNumber().equals(userFromDb.getContactNumber()))
                userFromDb.setContactNumber(user.getContactNumber());

            if (!user.getAddress().equals(userFromDb.getAddress()))
                userFromDb.setAddress(user.getAddress());

            if (!user.isActive() == userFromDb.isActive())
                userFromDb.setActive(user.isActive());

            this.controller.saveUser(userFromDb);

            model.addAttribute("user", userFromDb);
            model.addAttribute("msg","User has been saved");

            System.out.println("ALL USERS PRINTING");
            System.out.println("ALL USERS PRINTING");
            this.controller.getAllUsers().stream().map(abc -> (ExtUser)abc).forEach(System.out::println);
            System.out.println("ALL USERS PRINTING DONE");
//            System.out.println(model.asMap().get("user"));
            return "user";
        }
//        if(true)
//            throw new RuntimeException("No user found in DB with id"+user.getId());
        redirectAttrs.addAttribute("errorMsg","Unable to find user listed in DB");
        return "redirect:error";
    }

    @RequestMapping(value = "DisableUser", method = RequestMethod.POST)
    public String removeUser(@RequestParam("userId") int userId, Model model) {
        Optional<UserInterface> user = this.controller.getUserById(userId);
        if (user.isPresent()) {
            ((ExtUser) user.get()).setActive(false);
            this.controller.saveUser(user.get());
            model.addAttribute("action", "user disabled");
            model.addAttribute("userId", Long.toString(user.get().getId()));

            return "a3n/disabled";
        }
        model.addAttribute("error", "Unable to find user with id");
        return "error";

    }

    @RequestMapping("test")
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/")
    public String main() {
        return "test";
    }


    // https://stackoverflow.com/questions/25625056/how-to-obtain-csrf-token-in-a-velocity-macro-when-using-spring-security/25639809
    @ResponseBody
    @RequestMapping("/csrf")
    public CsrfToken token(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return token;
    }
}
