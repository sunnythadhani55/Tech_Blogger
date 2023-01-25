package com.blogger.tech.service;

import java.util.List;

import com.blogger.tech.dto.ArticleDTO;
import com.blogger.tech.dto.ArticleStatusHistoryDTO;
import com.blogger.tech.exception.ResourceAlreadyExists;
import com.blogger.tech.exception.ResourceNotFoundException;

public interface ArticleService {  
    
	
    List<ArticleDTO> getAll();
    
    ArticleDTO getById(Long articleId) throws ResourceNotFoundException;
    
    ArticleDTO add(ArticleDTO articleDTO,Long userId) throws ResourceAlreadyExists;
    
    void createStatusHistory(ArticleStatusHistoryDTO articleStatusHistoryDTO);
    
    ArticleDTO update(ArticleDTO articleDTO, Long userId) throws ResourceNotFoundException;
    
    void deleteById(Long articleId) throws ResourceNotFoundException;
    
    List<ArticleDTO> getPublishedArticlesByTag(Long tagId) throws ResourceNotFoundException;
    
    List<ArticleDTO> getAllByUser(Long userId);

	void updateStatus(ArticleStatusHistoryDTO articleStatusHistoryDTO, Long userId);
    
}
