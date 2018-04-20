package com.icrn.substitute.scheduler.substitute_scheduler;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.dao.MongoUserRepository;
import com.icrn.substitutes.Controller;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestInitializer implements SmartInitializingSingleton {


    @Autowired
    Controller controller;

    @Autowired
    MongoUserRepository userRepository;

    @Override
    public void afterSingletonsInstantiated() {

        System.out.println("I'M ADDING DATA");

        ExtUser admin = new ExtUser();
        admin.setId(1L);
        admin.addRole("ROLE_ADMIN");
        admin.addRole("ROLE_USER");
        admin.setName("admin");
        admin.setActive(true);
        admin.setPassword("{bcrypt}$2a$10$CQvzWAEvOqVJteZpfNf7HOTh.FDggzNkrRpT6x14yfiF9UrS4r7zy");

        ExtUser user = new ExtUser();
        user.setId(2L);
        user.addRole("ROLE_USER");
        user.setName("user");
        user.setActive(true);
        user.setPassword("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq");

        ExtUser disabledUser = new ExtUser();
        disabledUser.setId(3L);
        disabledUser.addRole("ROLE_USER");
        disabledUser.setName("disabled");
        disabledUser.setActive(false);
        disabledUser.setPassword("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq");

        this.controller.saveUser(admin);
        this.controller.saveUser(user);
        this.controller.saveUser(disabledUser);

        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(disabledUser);


    }
}
