package com.blogger.tech.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import com.blogger.tech.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {

	private Long id;
	
	@NotEmpty(message = "Tag Name must not be null or empty")
	private String name;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private List<UserDTO> userDTOList;
	
	private List<ArticleDTO> articleDTOList;
	
	
	public TagDTO(Tag tag,Boolean loadUserDTOList, Boolean loadArticleDTOList) {
		this.id=tag.getId();
		this.name=tag.getName();
		this.createdAt=tag.getCreatedAt();
		this.updatedAt=tag.getUpdatedAt();
		
		if(loadUserDTOList && tag.getUserList()!=null) {
			this.userDTOList=tag.getUserList().stream()
					.map(user -> new UserDTO(user, true, true, true))
					.collect(Collectors.toList());			
		}
		
		if(loadArticleDTOList && tag.getArticleList()!=null) {
			this.articleDTOList=tag.getArticleList().stream()
						.map(article -> new ArticleDTO(article, true, true, false))
						.collect(Collectors.toList());
		}
	}	
	
}
