package com.icrn.substitute.scheduler.substitute_scheduler.dao;

import com.icrn.substitute.scheduler.substitute_scheduler.Domain.ExtUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoUserRepository extends MongoRepository<ExtUser,Long> {
    ExtUser findUserById(Long id);
    List<ExtUser> findByName(String name);
    List<ExtUser> findAll();
}
