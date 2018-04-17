package com.icrn.substitute.scheduler.substitute_scheduler.configuration;

import com.icrn.substitute.scheduler.substitute_scheduler.dao.UserRepository;
import com.icrn.substitutes.model.User;
import org.springframework.security.core.GrantedAuthority;
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    static class CustomUserDetails extends User implements UserDetails{
        public CustomUserDetails(User user) {
            CustomUserDetails customUserDetails =new CustomUserDetails();
            customUserDetails.setName(user.getName());
            customUserDetails.setId(user.getId());
            customUserDetails.setAddress(user.getAddress());
            customUserDetails.setContactNumber(user.getContactNumber());

        }

        public CustomUserDetails() {
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }
}
