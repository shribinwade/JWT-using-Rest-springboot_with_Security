package com.jwtDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtDemo.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
