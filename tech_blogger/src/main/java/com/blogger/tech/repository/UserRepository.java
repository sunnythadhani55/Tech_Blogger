package com.blogger.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogger.tech.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
