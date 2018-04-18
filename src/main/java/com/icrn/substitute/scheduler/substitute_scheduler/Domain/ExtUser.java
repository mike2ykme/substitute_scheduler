package com.icrn.substitute.scheduler.substitute_scheduler.Domain;

import com.icrn.substitutes.model.UserInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtUser implements UserInterface {
    private String password;
    private boolean active;
    private long id;
    private String name;
    private String contactNumber;
    private String Address;

    public ExtUser(ExtUser user) {
        this.password = user.getPassword();
        this.setId(user.getId());
        this.setName(user.getName());
        this.setAddress(user.getAddress());
        this.setContactNumber(user.getContactNumber());
        this.setActive(user.isActive());
    }

}
