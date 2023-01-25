package com.blogger.tech.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.blogger.tech.dto.ArticleDTO;
import com.blogger.tech.dto.ArticleStatusHistoryDTO;
import com.blogger.tech.enums.ArticleStatus;
import com.blogger.tech.exception.ResourceNotFoundException;
import com.blogger.tech.model.Article;
import com.blogger.tech.model.ArticleStatusHistory;
import com.blogger.tech.model.Tag;
import com.blogger.tech.model.User;
import com.blogger.tech.repository.ArticleRepository;
import com.blogger.tech.repository.ArticleStatusHistoryRepoistory;
import com.blogger.tech.repository.TagRepository;
import com.blogger.tech.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

	private final ArticleRepository articleRepository;

	private final UserRepository userRepository;
	
	private final TagRepository tagRepository;
	
	private final ArticleStatusHistoryRepoistory articleStatusHistoryRepoistory;
	
	@Override
	public List<ArticleDTO> getAll() {		
		return articleRepository.findAll()
				.stream()
				.map(article -> new ArticleDTO(article,false,false,false))
				.collect(Collectors.toList());
	}

	@Override
	public ArticleDTO getById(Long articleId){
	  Article article=articleRepository.findById(articleId)
			  .orElseThrow(() -> new ResourceNotFoundException("Article","Id",articleId.toString()));
	  
	  return new ArticleDTO(article,true,true,true);
	}

	@Override
	public ArticleDTO add(ArticleDTO articleDTO, Long userId){
		Article article=new Article(articleDTO);
		
		Optional<User> user=userRepository.findById(userId);
		article.setUser(user.get());
		
		Set<Tag> tagList=articleDTO.getTagDTOList().stream()
				.map(tagDTO -> tagRepository.findById(tagDTO.getId()).get())
				.collect(Collectors.toSet());
		
		article.setTagList(tagList);
		
		articleRepository.save(article);
		
		articleDTO=new ArticleDTO(article,true,true,false);
		
		ArticleStatusHistoryDTO articleStatusHistoryDTO=new ArticleStatusHistoryDTO(article, true, true);
		
		createStatusHistory(articleStatusHistoryDTO);
		
		return articleDTO;
	}
	
	@Override
	public void createStatusHistory(ArticleStatusHistoryDTO articleStatusHistoryDTO) {
		ArticleStatusHistory articleStatusHistory=new ArticleStatusHistory(articleStatusHistoryDTO, true, true);
		articleStatusHistoryRepoistory.save(articleStatusHistory);
	}
	
	@Override
	public ArticleDTO update(ArticleDTO articleDTO, Long userId){
		
		 Long articleId=articleDTO.getId();
		
		 Article article=articleRepository.findById(articleId)
				  .orElseThrow(() -> new ResourceNotFoundException("Article","Id",articleId.toString())); 
		
		 ArticleStatus articleStatus=article.getStatus();
		 
		 articleDTO.setCreatedAt(article.getCreatedAt());
		 article=new Article(articleDTO);
		 
		 Set<Tag> tagList=articleDTO.getTagDTOList().stream()
					.map(tagDTO -> tagRepository.findById(tagDTO.getId()).get())
					.collect(Collectors.toSet());
		 
		 article.setTagList(tagList);
		 
		 Optional<User> user=userRepository.findById(userId);
			article.setUser(user.get());
		 
		 articleRepository.save(article);
		 
		 articleDTO=new ArticleDTO(article,true,true,false);
		 
		 switch(articleStatus) {
		 	case DRAFT : 
		 		switch(articleDTO.getStatus()) {
		 			case DRAFT :
		 				break;
		 			case UNDER_REVIEW :
		 				ArticleStatusHistoryDTO articleStatusHistoryDTO=new ArticleStatusHistoryDTO(article, true, true);
		 				createStatusHistory(articleStatusHistoryDTO);
		 				break;
		 			case PUBLISHED : case REJECTED :
		 				//throw an exception		 				
		 		}
		 		break;
		 	case UNDER_REVIEW :
		 		switch(articleDTO.getStatus()) {
	 			case DRAFT :
	 				ArticleStatusHistoryDTO articleStatusHistoryDTO=new ArticleStatusHistoryDTO(article, true, true);
	 				createStatusHistory(articleStatusHistoryDTO);
	 				break;
	 			case UNDER_REVIEW :
	 				break;
	 			case PUBLISHED : case REJECTED :
	 				//throw an exception	 				
		 		}
		 		break;
		 	case REJECTED :
		 		switch(articleDTO.getStatus()) {		 		 				 				
	 			case DRAFT, UNDER_REVIEW :
	 				ArticleStatusHistoryDTO articleStatusHistoryDTO=new ArticleStatusHistoryDTO(article, true, true);
	 				createStatusHistory(articleStatusHistoryDTO);
	 				break;
	 			case PUBLISHED, REJECTED :
	 				//throw an exception	 				
		 		}
		 		break;
		 	case PUBLISHED :
		 		break;
		  }
		 
		 return articleDTO;	 
	}

	@Override
	public void deleteById(Long articleId){
		if(articleRepository.existsById(articleId)) 
			articleRepository.deleteById(articleId);
		else
			throw new ResourceNotFoundException("Article","Id",articleId.toString());
			
	}

	@Override
	public List<ArticleDTO> getPublishedArticlesByTag(Long tagId) throws ResourceNotFoundException {
		Tag tag= tagRepository.findById(tagId)
			.orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId.toString()));
		
		return tag.getArticleList().stream()
				.filter(article -> article.getStatus()==ArticleStatus.PUBLISHED)
				.map(publishedArticle -> new ArticleDTO(publishedArticle, false, false,false))
				.collect(Collectors.toList());
	}

	@Override
	public List<ArticleDTO> getAllByUser(Long userId) {
		
		return userRepository.findById(userId).get().getArticleList()
				.stream()
				.map(article -> new ArticleDTO(article, false, false, false))
				.collect(Collectors.toList());
	}

	@Override
	public void updateStatus(ArticleStatusHistoryDTO articleStatusHistoryDTO, Long userId) {
		
		Long articleId=articleStatusHistoryDTO.getArticleDTO().getId();

		Article article=articleRepository.findById(articleId)
				  .orElseThrow(() -> new ResourceNotFoundException("Article","Id",articleId.toString())); 
		
		 
		 Optional<User> user=userRepository.findById(userId);
			article.setUser(user.get());
		
		switch(article.getStatus()) {
	 		case UNDER_REVIEW : 
		 		switch(articleStatusHistoryDTO.getCurrentStatus()) {
		 			case REJECTED, PUBLISHED :
		 				articleRepository.updateStatus(articleStatusHistoryDTO.getCurrentStatus(),articleId);
		 				article.setStatus(articleStatusHistoryDTO.getCurrentStatus());
		 				article.setUpdatedAt(LocalDateTime.now());
		 				articleStatusHistoryDTO=new ArticleStatusHistoryDTO(article, true, true);
		 				createStatusHistory(articleStatusHistoryDTO);
		 				break;
		 			}
		 		break;	
	 	  }
	}

}
