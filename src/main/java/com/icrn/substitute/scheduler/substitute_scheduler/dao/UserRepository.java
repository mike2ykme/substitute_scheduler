package com.icrn.substitute.scheduler.substitute_scheduler.dao;

import com.icrn.substitutes.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,Long> {
    public User findUserById(Long id);
    public List<User> findByName(String name);
    public List<User> findAll();
}
