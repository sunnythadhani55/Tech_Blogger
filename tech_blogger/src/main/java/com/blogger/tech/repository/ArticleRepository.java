package com.blogger.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blogger.tech.enums.ArticleStatus;
import com.blogger.tech.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

  @Modifying
  @Query("UPDATE Article a set a.status=:status where a.id=:articleId")
  public void updateStatus(ArticleStatus status, Long articleId);

}
