package com.blogger.tech.controller;

import java.util.List;

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

import com.blogger.tech.dto.CollectionDTO;
import com.blogger.tech.service.CollectionService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("api/v1/collections")
@RequiredArgsConstructor
public class CollectionController {

	private final CollectionService collectionService;
	
	@GetMapping
	public List<CollectionDTO> getAllByUserId(){
		Long userId=1L;
		return collectionService.getAllByUserId(userId);	
	}
	
	@GetMapping("/{collectionId}")
	public CollectionDTO getById(@PathVariable Long collectionId){
		Long userId=1L;
		return collectionService.getById(collectionId,userId);		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CollectionDTO add(@RequestBody CollectionDTO collectionDTO) {
		Long userId=1L;
		return collectionService.add(collectionDTO,userId);
	}
	
	@PutMapping
	public CollectionDTO update(@RequestBody CollectionDTO collectionDTO) {
		Long userId=1L;
		return collectionService.update(collectionDTO,userId);
	}
	
	@DeleteMapping("/{collectionId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long collectionId){
		Long userId=1L;
		collectionService.deleteById(collectionId,userId);
	}
	
}
