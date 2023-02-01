package com.blogger.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogger.tech.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  boolean existsByName(String tagName);
}
