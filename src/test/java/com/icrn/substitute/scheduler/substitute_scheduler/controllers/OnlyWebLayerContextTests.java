package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest //Here we're asking for all the controllers, could ask for a single controller w/like @WebMvcTest(HelloController.class)
@WebMvcTest(BaseController.class)
public class OnlyWebLayerContextTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    public void load(){

    }

    @Ignore
    @Test
    public void shouldReturnDefaultMessage() throws Exception{
        this.mockMvc.perform(get("/"))
                        .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(content().string(containsString("Hello World")));
    }

}
