package com.blogger.tech.service;

import java.util.List;

import com.blogger.tech.dto.CollectionDTO;
import com.blogger.tech.exception.ResourceAlreadyExists;
import com.blogger.tech.exception.ResourceNotFoundException;

public interface CollectionService {

	List<CollectionDTO> getAllByUserId(Long userId);
	
	CollectionDTO getById(Long collectionId, Long userId) throws ResourceNotFoundException;
	
	CollectionDTO add(CollectionDTO collectionDTO, Long userId) throws ResourceAlreadyExists;
	
	CollectionDTO update(CollectionDTO collectionDTO, Long userId) throws ResourceNotFoundException;
	
	void deleteById(Long collectionId,Long userId) throws ResourceNotFoundException;
}
