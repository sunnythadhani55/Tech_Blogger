package com.blogger.tech.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.blogger.tech.model.Collection;

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
public class CollectionDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private UserDTO userDTO;
	
	private Set<ArticleDTO> articleDTOList;
	
	
	public CollectionDTO(Collection collection, Boolean loadUserDTO, Boolean loadArticleDTOList) {
		
		this.id=collection.getId();
		this.name=collection.getName();
		this.description=collection.getDescription();
		this.createdAt=collection.getCreatedAt();
		this.updatedAt=collection.getUpdatedAt();
		
		if(loadUserDTO && collection.getUser()!=null) {
			this.userDTO=new UserDTO(collection.getUser(), false, false, false);
		}
		
		if(loadArticleDTOList && collection.getArticleList()!=null) {
			this.articleDTOList=collection.getArticleList().stream()
						.map(article -> new ArticleDTO(article, false, false,false))
						.collect(Collectors.toSet());
		}
	}


	@Override
	public int hashCode() {
		return Objects.hash(articleDTOList, createdAt, description, id, name, updatedAt, userDTO);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectionDTO other = (CollectionDTO) obj;
		return Objects.equals(articleDTOList, other.articleDTOList) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(updatedAt, other.updatedAt)
				&& Objects.equals(userDTO, other.userDTO);
	}
	
	
}
