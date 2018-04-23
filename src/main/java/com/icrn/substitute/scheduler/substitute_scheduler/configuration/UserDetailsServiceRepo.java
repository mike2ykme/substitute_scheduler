package com.icrn.substitute.scheduler.substitute_scheduler.configuration;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitutes.Controller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceRepo implements UserDetailsService {

    Controller controller;

    public UserDetailsServiceRepo(Controller controller) {
        this.controller = controller;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return this.controller.getAllUsers().stream()
                .filter(user -> user.getName().equals(name))
                .map(user -> new CustomUserDetails((ExtUser)user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No User Found!!!"));
    }

    static class CustomUserDetails extends ExtUser implements UserDetails{
        public CustomUserDetails(ExtUser user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<String> formattedRoles = this.getRoles()
                                            .stream()
                                            .map(role -> "ROLE_".concat(role))
                                            .collect(Collectors.toList());

            String[] roles = formattedRoles.toArray(new String[this.getRoles().size()]);
            return AuthorityUtils.createAuthorityList(roles);

//            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }

        @Override
        public String getPassword() {

            return super.getPassword();
        }

        @Override
        public String getUsername() {
            return getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return super.isActive();
        }

        @Override
        public boolean isAccountNonLocked() {
            return super.isActive();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return super.isActive();
        }

        @Override
        public boolean isEnabled() {
            return super.isActive();
        }
    }
}
