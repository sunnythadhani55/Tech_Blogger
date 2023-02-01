package com.blogger.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogger.tech.model.ArticleStatusHistory;

public interface ArticleStatusHistoryRepoistory extends JpaRepository<ArticleStatusHistory, Long> {

}
