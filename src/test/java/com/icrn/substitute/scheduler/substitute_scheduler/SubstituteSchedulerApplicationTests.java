package com.icrn.substitute.scheduler.substitute_scheduler;

import com.icrn.substitute.scheduler.substitute_scheduler.controllers.BaseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


//Default test that came with Spring Boot
// https://spring.io/guides/gs/testing-web/
@RunWith(SpringRunner.class)
@SpringBootTest //Tells Spring that it needs to load the main application context from a main class with @SpringBootApplication on it
public class SubstituteSchedulerApplicationTests {

    @Autowired
    BaseController controller;

    @Test
    public void contextLoads() {
        assertThat(controller,is(not(nullValue())));
    }

}
