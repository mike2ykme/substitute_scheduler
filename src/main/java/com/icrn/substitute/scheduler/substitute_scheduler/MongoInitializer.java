package com.icrn.substitute.scheduler.substitute_scheduler;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.dao.RequestRepository;
import com.icrn.substitutes.dao.SubstituteRepository;
import com.icrn.substitutes.dao.UserRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoInitializer implements SmartInitializingSingleton {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubstituteRepository substituteRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    Controller controller;

    @Override
    public void afterSingletonsInstantiated() {
        ExtUser admin = new ExtUser();
        admin.setId(1L);
        admin.setName("admin");
        admin.setActive(true);
        admin.setPassword("{bcrypt}$2a$10$CQvzWAEvOqVJteZpfNf7HOTh.FDggzNkrRpT6x14yfiF9UrS4r7zy");

        ExtUser user = new ExtUser();
        user.setId(2L);
        user.setName("user");
        user.setActive(true);
        user.setPassword("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq");

        ExtUser disabledUser = new ExtUser();
        disabledUser.setId(3L);
        disabledUser.setName("disabled");
        disabledUser.setActive(false);
        disabledUser.setPassword("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq");

        this.controller.saveUser(admin);
        this.controller.saveUser(user);
        this.controller.saveUser(disabledUser);
//        this.userRepository.save(admin);
//        this.userRepository.save(user);
//        this.userRepository.save(disabledUser);
    }
}

//
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("{bcrypt}$2a$10$CQvzWAEvOqVJteZpfNf7HOTh.FDggzNkrRpT6x14yfiF9UrS4r7zy")
//                .roles("USER","ADMIN");
//    }