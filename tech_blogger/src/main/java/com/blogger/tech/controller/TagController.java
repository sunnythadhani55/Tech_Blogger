package com.blogger.tech.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogger.tech.dto.TagDTO;
import com.blogger.tech.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagService tagService;

  @GetMapping
  public List<TagDTO> getAll() {
    return tagService.getAll();
  }

  @GetMapping("/{tagId}")
  public TagDTO getById(@PathVariable Long tagId) {
    return tagService.getById(tagId);
  }

  @GetMapping("/user")
  public List<TagDTO> getAllSubscribed() {
    Long userId = 1L;
    return tagService.getAllSubscribed(userId);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public TagDTO add(@Valid @RequestBody TagDTO tagDTO) {
    return tagService.add(tagDTO);
  }

  @PutMapping
  public TagDTO update(@RequestBody TagDTO tagDTO) {
    return tagService.update(tagDTO);
  }

  @DeleteMapping("/{tagId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long tagId) {
    tagService.deleteById(tagId);
  }

  @PostMapping("/subscribe/{tagId}")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void subscribe(@PathVariable Long tagId) {
    Long userId = 1L;
    tagService.subscribe(tagId, userId);
  }

  @DeleteMapping("/unsubscribe/{tagId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void unsubscribe(@PathVariable Long tagId) {
    Long userId = 1L;
    tagService.unsubscribe(tagId, userId);
  }


}
