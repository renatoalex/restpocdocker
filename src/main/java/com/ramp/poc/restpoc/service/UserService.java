package com.ramp.poc.restpoc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ramp.poc.restpoc.model.User;

@Service
public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    User update(Long id, User user);

    void deleteById(Long id);

    User create(User user);

}
