package com.icrn.substitute.scheduler.substitute_scheduler.configuration;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitutes.dao.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDetailsServiceRepo implements UserDetailsService {

    UserRepository userRepository;

    public UserDetailsServiceRepo(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        System.out.println("HERE");
        System.out.println("HERE");
        System.out.println("HERE");
        System.out.println("HERE");
        System.out.println("HERE");
        System.out.println("HERE");
        return this.userRepository.getAllusers()
                        .stream().filter(u -> u.getName() == name)
                        .peek(System.out::println)
                        .map(u ->new CustomUserDetails((ExtUser)u))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No User Found"));
//        return this.userRepository.findByName(name);
    }

    static class CustomUserDetails extends ExtUser implements UserDetails{
        public CustomUserDetails(ExtUser user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }

        @Override
        public String getPassword() {

            return this.getPassword();
        }

        @Override
        public String getUsername() {
            return this.getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return this.isEnabled();
        }

        @Override
        public boolean isAccountNonLocked() {
            return this.isEnabled();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return this.isEnabled();
        }

        @Override
        public boolean isEnabled() {
            return this.isEnabled();
        }
    }
}
