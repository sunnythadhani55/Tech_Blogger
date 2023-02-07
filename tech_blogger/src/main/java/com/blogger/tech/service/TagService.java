package com.blogger.tech.service;

import java.util.List;
import com.blogger.tech.dto.TagDTO;
import com.blogger.tech.exception.ResourceAlreadyExistsException;
import com.blogger.tech.exception.ResourceNotFoundException;

public interface TagService {

  List<TagDTO> getAll();

  TagDTO getById(Long tagId) throws ResourceNotFoundException;

  List<TagDTO> getAllSubscribed(Long userId);

  TagDTO add(TagDTO tagDTO) throws ResourceAlreadyExistsException;

  TagDTO update(TagDTO tagDTO) throws ResourceNotFoundException;

  void deleteById(Long tagId) throws ResourceNotFoundException;

  void subscribe(Long tagId, Long userId) throws ResourceNotFoundException;

  void unsubscribe(Long tagId, Long userId) throws ResourceNotFoundException;
}
