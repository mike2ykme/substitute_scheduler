package com.icrn.substitute.scheduler.substitute_scheduler.controllers;
import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitute.scheduler.substitute_scheduler.configuration.UserDetailsServiceRepo;
import com.icrn.substitutes.Controller;
import com.icrn.substitutes.Exceptions.SchedulingException;
import com.icrn.substitutes.model.*;
import com.icrn.substitutes.model.enumerations.Status;
import org.junit.Assert;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
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

    @Test
    public void userPageGenerates() throws Exception{
        ResultActions results = mockMvc.perform(get("/user/1").with(user(userDetailsService.loadUserByUsername("user"))));

        results
                .andExpect(view().name("user"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user",is(this.controller.getUserById(1).get())));
    }

    @Test
    public void invalidUserPageGeneratesError() throws Exception{
        ResultActions results = mockMvc.perform(get("/user/3333333333333").with(user(userDetailsService.loadUserByUsername("user"))));

        results
                .andExpect(view().name("error"))
                .andExpect(model().attribute("user",is(not(this.controller.getUserById(1).get()))));
    }
    @Test
    public void createRequest() throws Exception{

        Request request = new Request();
        request.setRequestorId(1L);
        request.setSubstituteId(2L);
        request.setEndTime(LocalDateTime.now());
        request.setStartTime(LocalDateTime.now());
        request.setStatus(Status.OPEN);
        ResultActions results = mockMvc.perform(
                        post("/createRequest")
                                .with(user(userDetailsService.loadUserByUsername("user"))).with(csrf())
                                .flashAttr("request",request)
        );

        results
                .andExpect(view().name("request"))
                .andExpect(model().attribute("request",hasProperty("requestorId",is(1L))))
                .andExpect(model().attribute("request",hasProperty("substituteId",is(2L))))
                .andExpect(model().attribute("request",hasProperty("requestId",is(not(nullValue())))))
        ;
    }

    @Test()
    public void createRequestExpectException(){
        try {
            ResultActions results = mockMvc.perform(
                    post("/createRequest")
                            .with(user(userDetailsService.loadUserByUsername("user"))).with(csrf())
                            .param("requestorId", "1")
                            .param("substituteId", "2")
                            .flashAttr("request", new Request())
            );

        }catch (Exception e){
            Assert.assertTrue(e.getMessage().contains("request object is invalid"));
        }
    }

    @Test
    public void scheduleRequestFull() throws Exception{
        //given
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
        sub.setScheduledTimes(new AvailabilitySet());

        UserInterface user = new User();
        user.setId(1111111111L);
        //Add a substitute
        assertThat(this.controller.saveSubstitute(sub),is(not(nullValue())));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd).isEmpty(),
                is(not(nullValue())));
        //make sure we can find a substitute user for a specific time
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd),
                hasItem(sub));
        List<Substitute> substituteList = this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd);

        //make request and ensure it is scheduled. We then need to ensure we don't double book someone
//        Request request = null;
        Request request1 = new Request();
        request1.setEndTime(notHolidayEnd);
        request1.setStartTime(notHolidayStart);
        request1.setSubstituteId(substituteList.get(0).getId());
        request1.setRequestorId(user.getId());
//        request = request1;

        ResultActions results = mockMvc
                .perform(
                        post("/scheduleSubstitute")
                                .with(user(userDetailsService.loadUserByUsername("user")))
                                .with(csrf())
                        .flashAttr("request", request1));

        results
                .andExpect(view().name("request"))
                .andExpect(model().attribute("status","scheduled"))
                .andExpect(model().attribute("request",is(request1)))
        ;

    }

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