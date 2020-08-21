package com.charter.springCacheRedis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.charter.springCacheRedis.model.User;
import com.charter.springCacheRedis.repository.UserRepository;

@RestController
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired 
    private UserRepository userRepository;


    @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 12000")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long userId) {
        LOG.info("Getting user with ID {}.", userId);
        return userRepository.findById(userId).orElse(null);
    }

    @CachePut(value = "users", key = "#user.id")
    @PutMapping("/update")
    public User updatePersonByID(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @CacheEvict(value = "users", allEntries=true)
    @DeleteMapping("/{userId}")
    public void deleteUserByID(@PathVariable Long userId) {
        LOG.info("deleting person with id {}", userId);
        userRepository.deleteById(userId);
    }
}
