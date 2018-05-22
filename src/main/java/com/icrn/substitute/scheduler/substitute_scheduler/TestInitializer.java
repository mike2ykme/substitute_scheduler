package com.icrn.substitute.scheduler.substitute_scheduler;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.dao.MongoUserRepository;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.model.Availability;
import com.icrn.substitutes.model.AvailabilitySet;
import com.icrn.substitutes.model.StartEnd;
import com.icrn.substitutes.model.Substitute;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;

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
        admin.setContactNumber("123 123 1234");
//        admin.addRole("ROLE_ADMIN");
        admin.addRole("ADMIN");
//        admin.addRole("ROLE_USER");
        admin.addRole("USER");
        admin.setName("admin");
        admin.setActive(true);
        admin.setPassword("{bcrypt}$2a$10$CQvzWAEvOqVJteZpfNf7HOTh.FDggzNkrRpT6x14yfiF9UrS4r7zy");

        ExtUser user = new ExtUser();
        user.setId(2L);
        user.addRole("USER");
        user.setName("user");
        user.setActive(true);
        user.setContactNumber("123 123 1234");
        user.setPassword("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq");

        ExtUser disabledUser = new ExtUser();
        disabledUser.setId(3L);
        disabledUser.addRole("USER");
        disabledUser.setName("disabled");
        disabledUser.setActive(false);
        disabledUser.setPassword("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq");



        AvailabilitySet availabilitySet = new AvailabilitySet();
        Availability availability = new Availability();
        LocalDateTime aHoliday = LocalDateTime.of(2011,11,
                11,11,11);
        LocalDateTime notHolidayStart = LocalDateTime.of(2018,4,5,9,0);
        LocalDateTime notHolidayEnd = LocalDateTime.of(2018,4,5,17,0);
        availabilitySet.addDay(LocalDate.of(2011,11,11));
        for(int i=0; i <=5;i++){
            availability.addAvailabilityTime(i,new StartEnd(LocalTime.of(5,0),
                    LocalTime.of(17,0)));
        }

        Substitute sub = new Substitute();
        sub.setId(1234567l);
        sub.setName("tester");
        sub.setAddress("123 Fake Street");
        sub.setContactNumber("1234567890");

        sub.setHolidayAvailability(availabilitySet);
        sub.setRegularAvailability(availability);
        sub.setScheduledTimes(new AvailabilitySet(new HashSet<>()));


        Substitute sub2 = new Substitute();
        sub2.setId(23456789);
        sub2.setName("SUB2");
        sub2.setAddress("321 Fake Street");
        sub2.setContactNumber("2345678901");
        sub2.setHolidayAvailability(availabilitySet);
        sub2.setRegularAvailability(availability);
        sub2.setScheduledTimes(new AvailabilitySet(new HashSet<>()));

        this.controller.saveSubstitute(sub2);
        this.controller.saveSubstitute(sub);
        this.controller.saveUser(admin);
        this.controller.saveUser(user);
        this.controller.saveUser(disabledUser);

        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(disabledUser);


    }
}
