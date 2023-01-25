package com.blogger.tech.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.blogger.tech.enums.ArticleStatus;
import com.blogger.tech.model.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
	
	private Long id;
	
	@NotEmpty(message = "Title must not be null or empty")
	private String title;
	
	@NotEmpty(message = "Content must not be null or empty")
	@Size(max = 2000)
	private String content;
	
	@NotNull(message = "Article Status must not be null or empty")
	private ArticleStatus status;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private UserDTO userDTO;
	
	private List<TagDTO> tagDTOList;
	
	private List<CollectionDTO> collectionDTOList;
	
	private List<ArticleStatusHistoryDTO> articleStatusHistoryDTOList;
	
	public ArticleDTO(Article article, Boolean loaduserDTO, 
					Boolean loadTagDTOList, Boolean loadStatusHistoryDTOList) {
			
		this.id = article.getId();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.status = article.getStatus();
		this.createdAt = article.getCreatedAt();
		this.updatedAt = article.getUpdatedAt();
		
		if(loaduserDTO && article.getUser()!=null) {
			this.userDTO=new UserDTO(article.getUser(), false, false, false);
		}
		
		if(loadTagDTOList && article.getTagList()!=null) {
			this.tagDTOList=article.getTagList().stream()
					.map(tag -> new TagDTO(tag, false, false))
					.collect(Collectors.toList());
		}
		
		if(loadStatusHistoryDTOList && article.getArticleStatusHistoryList()!=null) {
			this.articleStatusHistoryDTOList = article.getArticleStatusHistoryList().stream()
					.map(articleStatusHistory -> new ArticleStatusHistoryDTO(articleStatusHistory, true))
					.collect(Collectors.toList());
		}

	}
	
}
