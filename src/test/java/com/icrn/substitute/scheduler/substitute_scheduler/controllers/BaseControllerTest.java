package com.icrn.substitute.scheduler.substitute_scheduler.controllers;
import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.configuration.UserDetailsServiceRepo;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.model.Request;
import com.icrn.substitutes.model.Substitute;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//https://aggarwalarpit.wordpress.com/2017/05/17/mocking-spring-security-context-for-unit-testing/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserDetailsServiceRepo userDetailsService;

    @Autowired
    Controller controller;


    @Test
    public void showAllUsers() throws Exception{
        mockMvc.perform(get("/showAllUsers")
                .with(user(userDetailsService.loadUserByUsername("admin"))))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("allUsers"))
                .andExpect(
                        model().attribute("allUsers",hasItem(controller.getUserById(2).get())))
                .andExpect(
                        model().attribute("allUsers",hasItem(controller.getUserById(1).get())))
        ;
    }

    @Test
    public void showAllSubstitutes() throws Exception{
        Substitute sub1 = new Substitute();
        sub1.setName("Name1");
        sub1.setId(1L);

        Substitute sub2 = new Substitute();
        sub2.setName("Name2");
        sub2.setId(2L);

        this.controller.saveSubstitute(sub1);
        this.controller.saveSubstitute(sub2);


        ResultActions results = mockMvc
                .perform(get("/showAllSubstitutes")
                        .with(user(userDetailsService.loadUserByUsername("user"))));

        results
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("allSubstitutes"))
                .andExpect(
                        model().attribute("allSubstitutes",hasItem(controller.getSubstituteById(1L).get())))
                .andExpect(
                        model().attribute("allSubstitutes",hasItem(controller.getSubstituteById(2L).get())))
        ;

    }

    @Test
    public void showAllRequests() throws Exception{
        Request req1 = new Request();
        req1.setRequestId(1L);
        Request req2 = new Request();
        req2.setRequestId(2L);

        this.controller.saveRequest(req1);
        this.controller.saveRequest(req2);

        ResultActions results = mockMvc
                .perform(get("/showAllRequests")
                    .with(user(userDetailsService.loadUserByUsername("user"))));

        results
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("allRequests"))
                .andExpect(
                        model().attribute("allRequests",hasItem(controller.getRequestByRequestId(1L).get())))
                .andExpect(
                        model().attribute("allRequests",hasItem(controller.getRequestByRequestId(2L).get())))
        ;
    }

//    @Test
//    public void createRequest() throws Exception{
//        ResultActions results = mockMvc
//                .perform(
//                        post("createRequest")
//                                .with(user(userDetailsService.loadUserByUsername("user")))
//                                .with(csrf()));
//        results.andExpect()
//    }

    //https://aggarwalarpit.wordpress.com/2017/05/17/mocking-spring-security-context-for-unit-testing/
//
//    @Before
//    public void setupMock() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void mockApplicationUser() {
//        ExtUser applicationUser = mock(ExtUser.class);
//        Authentication authentication = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
//    }

    

}