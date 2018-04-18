package com.icrn.substitute.scheduler.substitute_scheduler.dao;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import com.icrn.substitutes.dao.UserRepository;
import com.icrn.substitutes.model.UserInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ExtUserRepositoryInMemory implements UserRepository {
    private Map<Long, ExtUser> userMap;

    public ExtUserRepositoryInMemory() {
        this.userMap = new ConcurrentHashMap<>();
    }

    public ExtUserRepositoryInMemory(Map<Long, ExtUser> userMap) {
        this.userMap = userMap;
    }

//    public Optional<ExtUser> getExtUserById(long userId){
//
//
//        return this.userMap.entrySet().stream()
//                .filter(e -> e.getValue().getId() == userId)
//                .map(Map.Entry::getValue)
//                .findAny();
//    }

    @Override
    public Optional<UserInterface> getUserById(long userId) {
        return this.userMap.entrySet().stream()
                .filter(e -> e.getValue().getId() == userId)
                .map(e -> (UserInterface)e.getValue())
                .findAny();
    }

    @Override
    public UserInterface saveUser(UserInterface user) {
        if (user.getId() ==0)
            user.setId(Math.abs((new Random()).nextLong()));
        if (user instanceof ExtUser)
            this.userMap.put(user.getId(),(ExtUser)user);
        else
            throw new RuntimeException("User is not an ExtUser");
        return user;
    }

    @Override
    public List<UserInterface> getAllusers() {
        return this.userMap.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }
}
