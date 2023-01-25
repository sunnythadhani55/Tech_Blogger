package com.blogger.tech.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.blogger.tech.dto.CollectionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Collection {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLLECTION_SEQ")
	@SequenceGenerator(name = "COLLECTION_SEQ",
					sequenceName = "COLLECTION_SEQ", allocationSize = 1)
	private Long id;
	
	private String name;
	
	private String description;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "collection_article",
			joinColumns = @JoinColumn(name = "collection_id"),
			inverseJoinColumns = @JoinColumn(name="article_id")
				)
	private Set<Article> articleList;
	
	
	public Collection(CollectionDTO collectionDTO) {
		
		this.id=collectionDTO.getId();
		this.name=collectionDTO.getName();
		this.description=collectionDTO.getDescription();
		this.createdAt=collectionDTO.getCreatedAt()==null ? LocalDateTime.now() : collectionDTO.getCreatedAt();
		this.updatedAt=LocalDateTime.now();
		
	}
	
}
