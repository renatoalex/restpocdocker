package com.ramp.poc.restpoc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramp.poc.restpoc.model.User;
import com.ramp.poc.restpoc.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public List<User> findAll() {
        Iterable<User> iterable = repository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public User findById(final Long id) {
        Optional<User> findById = repository.findById(id);
        return findById.orElse(null);
    }

    @Override
    public User findByUsername(final String username) {
        Optional<User> findByUsername = repository.findByUsername(username);
        return findByUsername.orElse(null);
    }

    @Override
    public User update(final Long id, final User user) {
        Optional<User> findById = repository.findById(id);

        return findById.map((storedUser) -> {
            storedUser.setUsername(user.getUsername());
            return repository.save(storedUser);
        }).orElse(null);
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    @Override
    public User create(final User user) {
        return repository.save(user);
    }
}
