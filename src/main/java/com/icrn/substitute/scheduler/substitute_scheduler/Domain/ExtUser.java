package com.icrn.substitute.scheduler.substitute_scheduler.Domain;

import com.icrn.substitutes.model.UserInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ExtUser implements UserInterface {
    private String password;
    private boolean active;
    private long id;
    private String name;
    private String contactNumber;
    private String Address;
    private List<String> roles = new ArrayList<>();

    public ExtUser(ExtUser user) {
        this.password = user.getPassword();
        this.setId(user.getId());
        this.setName(user.getName());
        this.setAddress(user.getAddress());
        this.setContactNumber(user.getContactNumber());
        this.setActive(user.isActive());
        this.setRoles(user.getRoles());
    }
    public List<String> addRole(String role){
        this.roles.add(role);
        return Collections.unmodifiableList(roles);
    }

    public List<String> getRoles(){
        return Collections.unmodifiableList(roles);
    }

}
