package com.icrn.substitute.scheduler.substitute_scheduler.controllers;
import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//https://aggarwalarpit.wordpress.com/2017/05/17/mocking-spring-security-context-for-unit-testing/
public class BaseControllerTest {

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mockApplicationUser() {
        ExtUser applicationUser = mock(ExtUser.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
    }

    

}