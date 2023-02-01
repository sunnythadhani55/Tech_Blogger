package com.blogger.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogger.tech.model.User;
import com.blogger.tech.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

  List<Collection> findByUser(User user);
}
