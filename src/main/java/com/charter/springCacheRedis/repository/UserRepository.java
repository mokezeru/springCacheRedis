package com.charter.springCacheRedis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charter.springCacheRedis.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }