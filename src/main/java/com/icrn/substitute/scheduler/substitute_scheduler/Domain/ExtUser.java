package com.icrn.substitute.scheduler.substitute_scheduler.Domain;

import com.icrn.substitutes.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtUser extends User {
    private String password;
    private boolean active;
    public ExtUser(ExtUser user) {
        this.password = user.getPassword();
        this.setId(user.getId());
        this.setName(user.getName());
        this.setAddress(user.getAddress());
        this.setContactNumber(user.getContactNumber());
        this.setActive(user.isActive());
    }
}
