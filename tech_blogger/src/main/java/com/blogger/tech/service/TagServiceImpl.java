package com.blogger.tech.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.blogger.tech.dto.TagDTO;
import com.blogger.tech.exception.ResourceAlreadyExists;
import com.blogger.tech.exception.ResourceNotFoundException;
import com.blogger.tech.model.User;
import com.blogger.tech.model.Tag;
import com.blogger.tech.repository.UserRepository;
import com.blogger.tech.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class TagServiceImpl implements TagService{
	
	private final TagRepository tagRepository;
	
	private final UserRepository userRepository;

	@Override
	public List<TagDTO> getAll() {
		return tagRepository.findAll().stream()
				.map(tag -> new TagDTO(tag, false, false))
				.collect(Collectors.toList());
	}

	@Override
	public TagDTO getById(Long tagId) throws ResourceNotFoundException {
		Tag tag=tagRepository.findById(tagId)
					.orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId.toString()));
					
		return new TagDTO(tag, true, true);
	}

	@Override
	public TagDTO add(TagDTO tagDTO) throws ResourceAlreadyExists {		
		if(tagRepository.existsByName(tagDTO.getName())) {
			throw new ResourceAlreadyExists("Tag");
		}
		
		Tag tag=new Tag(tagDTO, false, false);
		tagRepository.save(tag);
		
		return new TagDTO(tag, false, false);
	}

	@Override
	public TagDTO update(TagDTO tagDTO) throws ResourceNotFoundException {
		Tag tag=tagRepository.findById(tagDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagDTO.getId().toString()));
		
		tagDTO.setCreatedAt(tag.getCreatedAt());
		tag=new Tag(tagDTO, false, false);
		
		tagRepository.save(tag);
		
		return new TagDTO(tag, false, true);
	}

	@Override
	public void deleteById(Long tagId) throws ResourceNotFoundException {
		if(tagRepository.existsById(tagId))
			tagRepository.deleteById(tagId);
		else
			throw new ResourceNotFoundException("Tag", "Id", tagId.toString());
	}

	@Override
	public void subscribe(Long tagId, Long userId) throws ResourceNotFoundException {
		Optional<User> user=userRepository.findById(userId);
		
		Tag tag=tagRepository.findById(tagId)
				.orElseThrow(() -> new ResourceNotFoundException("Tag", "Id",tagId.toString()));
		
		tag.getUserList().add(user.get());
		
		tagRepository.save(tag);
	}
	
	@Override
	public void unsubscribe(Long tagId, Long userId) throws ResourceNotFoundException {
		Optional<User> user=userRepository.findById(userId);
		
		Tag tag=tagRepository.findById(tagId)
				.orElseThrow(() -> new ResourceNotFoundException("Tag", "Id",tagId.toString()));
		
		tag.getUserList().remove(user.get());
		
		tagRepository.save(tag);
	}

	@Override
	public List<TagDTO> getAllSubscribed(Long userId) {
		Optional<User> user=userRepository.findById(userId);
		
		return user.get().getTagList().stream()
					.map(tag -> new TagDTO(tag, false, false))
					.collect(Collectors.toList());
		
	}

}