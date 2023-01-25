package com.blogger.tech.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.blogger.tech.dto.ArticleDTO;
import com.blogger.tech.enums.ArticleStatus;

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
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_SEQ")
	@SequenceGenerator(name = "ARTICLE_SEQ",
						sequenceName = "ARTICLE_SEQ", allocationSize = 1)
	private Long id;
	
	private String title;
	
	@Column(length = 2000)
	private String content;
	
	@Enumerated(EnumType.STRING)
	private ArticleStatus status;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(
		name = "article_tag",
		joinColumns = @JoinColumn(name="article_id"),
		inverseJoinColumns = @JoinColumn(name="tag_id"))
	private Set<Tag> tagList;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "collection_article",
			joinColumns = @JoinColumn(name = "article_id"),
			inverseJoinColumns = @JoinColumn(name="collection_id")
				)
	private Set<Collection> collectionList;

	@OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
	private List<ArticleStatusHistory> articleStatusHistoryList;
	
	public Article(ArticleDTO articleDTO) {
			
		this.id = articleDTO.getId();
		this.title = articleDTO.getTitle();
		this.content = articleDTO.getContent();
		this.status = articleDTO.getStatus();
		this.createdAt = articleDTO.getCreatedAt()==null ? LocalDateTime.now() : articleDTO.getCreatedAt();
		this.updatedAt = LocalDateTime.now();
		
	}
	
}
