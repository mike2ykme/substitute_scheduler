package com.icrn.substitute.scheduler.substitute_scheduler;

import com.icrn.substitutes.Controller;
import com.icrn.substitutes.dao.*;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


@SpringBootApplication
public class SubstituteSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubstituteSchedulerApplication.class, args);
    }

    @Bean
    public Controller getController(RequestRepository requestRepository, SubstituteRepository substituteRepository, UserRepository userRepository){
        return new Controller(requestRepository,substituteRepository, userRepository);
    }

    @Bean
    public UserRepository getUserRepository(){
        return new UserRepositoryInMemory();
    }
    @Bean
    public SubstituteRepository getSubstituteRepository(){
        return new SubstituteRepositoryInMemory();
    }
    @Bean
    public RequestRepository getRequestRepository(){
        return new RequestRepositoryInMemory();
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }
}
