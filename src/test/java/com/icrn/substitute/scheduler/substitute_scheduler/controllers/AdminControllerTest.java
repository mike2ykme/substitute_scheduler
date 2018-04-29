package com.icrn.substitute.scheduler.substitute_scheduler.controllers;

import com.icrn.substitute.scheduler.substitute_scheduler.configuration.UserDetailsServiceRepo;
import com.icrn.substitutes.Controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserDetailsServiceRepo userDetailsService;

    @Autowired
    Controller controller;

    // These test will load the full Spring Application context

//    @Test
    @WithMockUser
    public void shouldReturnGreeting() throws Exception {
        this.mockMvc.perform(get("/HelloWorld/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

//    So it seems that the actual guide written by people who write spring has a lot of issues
//            I mean the friggin thing lists things that you would have to know in order to
//    right now I'm just angry because I've spent over 3 hours trying to figure out that spring boot
//    doesn't have enough CRAP already pre loaded and I had to manually inject a spring dependency on top
//    of the dependency to other testing
//    I'm just saying that if you have the following in your docs you should be explicit:
//    https://github.com/spring-projects/spring-security/issues/2807
//    https://github.com/rwinch/spring-security-test-blog
//    https://docs.spring.io/spring-security/site/docs/current/reference/html/test-mockmvc.html
//    https://stackoverflow.com/questions/19163194/maven-dependency-for-org-springframework-test-web-server-samples-context-securit
//      Anyways, I had to manually load spring test from here to get the following to load the with user and password crap:
//
// https://mvnrepository.com/artifact/org.springframework.security/spring-security-test/5.0.4.RELEASE


    //   Also, this is another page that led me to creating tests
//    https://www.blazemeter.com/blog/spring-boot-rest-api-unit-testing-with-junit
    // https://github.com/spring-projects/spring-security/issues/2807
    @Test
    public void stillLearning() throws Exception{
        this.mockMvc.perform(get("/a3n/test")
                .with(user(userDetailsService.loadUserByUsername("admin"))).with(csrf())).andExpect(status().isOk());//.andExpect(content().string(containsString("Hello World")));

    }
    //    https://docs.spring.io/spring-security/site/docs/current/reference/html/test-mockmvc.html
    @Test
    public void shouldPostSuccess() throws Exception{
        mockMvc.perform(post("/postIt").with(user("admin").password("admin")).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("something")));
//                .andExpect(content().string("something"));

    }

    @Test
    public void expectCorrectPassword() throws Exception{
        mockMvc.perform(formLogin().user("admin").password("admin"))
                .andExpect(authenticated());
    }
//    @Test
    public void checkIncorrectPassword() throws Exception{
        mockMvc
                .perform(formLogin().password("invalid"))
                .andExpect(unauthenticated());
    }

//    @Test
    public void noAccssAnonymous() throws Exception{
        mockMvc.perform(post("/postIt").with(anonymous()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void showAllUsers() throws Exception{
        mockMvc.perform(get("/showAllUsers")
//                .with(user("admin").password("password")))
                .with(user(userDetailsService.loadUserByUsername("admin"))))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("allUsers"))
                .andExpect(
                        model().attribute("allUsers",hasItem(controller.getUserById(1).get())));
    }

    @Test()
    public void verifyAdminRoleAccessDenies() throws Exception{
        ResultActions results = mockMvc.perform(get("/a3n/test")
                .with(user(userDetailsService.loadUserByUsername("user"))));

        results
                .andExpect(status().is(403));

    }

//    @Test
//    public void stillLearning() throws Exception{
//        this.mockMvc.perform(get("/a3n/test")
//                .with(user("admin").password("admin"))).andExpect(status().isOk());//.andExpect(content().string(containsString("Hello World")));
//
//    }

    @Test()
    public void verifyAdminRoleAccessAllows() throws Exception{
        ResultActions results = mockMvc.perform(post("/a3n/test")
                .with(user(userDetailsService.loadUserByUsername("admin"))).with(csrf()));

        results
                .andExpect(status().isOk())
                .andExpect(view().name("test"));
    }

    @Test()
    public void verifyAddUser() throws Exception{

//        given(this.userDetailsService.loadUserByUsername("admin")).willReturn()
//        ResultActions results = mockMvc.perform(post("/a3n/AddUser").with(userDetailsService.loadUserByUsername("admin")));

        ResultActions results = mockMvc.perform(
                        post("/a3n/AddUser")
//                                .with(user(userDetailsService.loadUserByUsername("admin")))
                                .with(user(userDetailsService.loadUserByUsername("admin"))).with(csrf())
                                .param("name","Name")
                                .param("password","123")
                                .param("contactNumber","1234567890")
        );

        results
                .andExpect(status().isOk())
                .andExpect(model().attribute("user",is(not(nullValue()))))
        ;
    }
}