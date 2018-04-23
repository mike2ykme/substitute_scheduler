package com.icrn.substitute.scheduler.substitute_scheduler.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
            .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//            .inMemoryAuthentication()
//                .withUser("user")
//                    .password("{bcrypt}$2a$10$f928v5niYkw56YbQiMlA0OOHVJPqKOjahIfTVtQn/4z9LQzR/E0yq")
//                    .roles("USER")
//            .and()
//                .withUser("admin")
//                    .password("{bcrypt}$2a$10$CQvzWAEvOqVJteZpfNf7HOTh.FDggzNkrRpT6x14yfiF9UrS4r7zy")
//                    .roles("USER","ADMIN");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http
//            .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//            .and()
//                .httpBasic();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/a3n/**").hasAnyRole("ADMIN")//.hasRole("ADMIN")
                    .antMatchers("/**").hasAnyRole("USER","ADMIN") //.hasRole("USER,ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                .and()
                    .httpBasic();
//                .formLogin().and().csrf().disable();

    }

//    https://www.harinathk.com/spring/no-passwordencoder-mapped-id-null/
//    https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
