package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.service.GreetingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("/HelloWorld")
public class Test {

    private final GreetingService service;

    public Test(GreetingService service) {
        this.service = service;
    }

    //    @RequestMapping("/HelloWorld")
    @RequestMapping()
    public @ResponseBody String greeting() {
        return this.service.greet();
    }

}