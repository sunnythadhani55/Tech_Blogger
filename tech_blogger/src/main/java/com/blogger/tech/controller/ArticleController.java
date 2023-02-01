package com.blogger.tech.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogger.tech.dto.ArticleDTO;
import com.blogger.tech.dto.ArticleStatusHistoryDTO;
import com.blogger.tech.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;

  @GetMapping
  public List<ArticleDTO> getAll() {
    return articleService.getAll();
  }

  @GetMapping("/user")
  public List<ArticleDTO> getAllByUser() {
    Long userId = 10L;
    return articleService.getAllByUser(userId);
  }

  @GetMapping("/{articleId}")
  public ArticleDTO getById(@PathVariable Long articleId) {
    return articleService.getById(articleId);
  }

  @GetMapping("/tags/{tagId}")
  public List<ArticleDTO> getPublishedArticlesByTag(@PathVariable Long tagId) {
    return articleService.getPublishedArticlesByTag(tagId);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ArticleDTO add(@Valid @RequestBody ArticleDTO articleDTO) {
    Long userId = 1L;
    return articleService.add(articleDTO, userId);
  }

  @PatchMapping("/status")
  public void updateStatus(@RequestBody ArticleStatusHistoryDTO articleStatusHistoryDTO) {
    Long userId = 1L;
    articleService.updateStatus(articleStatusHistoryDTO, userId);
  }

  @PutMapping
  public ArticleDTO update(@Valid @RequestBody ArticleDTO articleDTO) {
    Long userId = 1L;
    return articleService.update(articleDTO, userId);
  }

  @DeleteMapping("/{articleId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long articleId) {
    articleService.deleteById(articleId);
  }

}
