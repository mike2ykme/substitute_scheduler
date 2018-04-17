package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
    Because I can never remember the imports:
    https://www.mkyong.com/unittest/hamcrest-how-to-assertthat-check-null-value/
*/
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@WebMvcTest(BaseController.class)
@AutoConfigureMockMvc
public class BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Controller controller;

    @Test
    public void contextLoads(){
        assertThat(mockMvc,is(not(nullValue())));
    }

    @Test
    public void canGetUsersIndex() throws  Exception{
        List<User> userList = new ArrayList<>();
        userList.add(new ExtUser());
        given(controller.getAllUsers())
                .willReturn(userList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("Users",is(userList)));
    }

    @Test
    public void test2() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
//                .andExpect(assertThat(true,is(true)))
                .andExpect(model().attribute("me",is(new User())))
                .andExpect(model().attribute("Users", new ArrayList<User>()));
    }

}