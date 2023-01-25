package com.blogger.tech.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.blogger.tech.dto.ArticleStatusHistoryDTO;
import com.blogger.tech.enums.ArticleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ArticleStatusHistory {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ARTICLE_STATUS_HISTORY_SEQ")
	@SequenceGenerator(
					name = "ARTICLE_STATUS_HISTORY_SEQ",
					sequenceName = "ARTICLE_STATUS_HISTORY_SEQ",
					allocationSize = 1)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private ArticleStatus currentStatus;
	
	private String message;
	
	private LocalDateTime updatedAt;
	
	@ManyToOne
	private Article article;
	
	@ManyToOne
	private User user;
	
	public ArticleStatusHistory(ArticleStatusHistoryDTO articleStatusHistoryDTO, Boolean loadUser, Boolean loadArticle) {
		
		this.currentStatus=articleStatusHistoryDTO.getCurrentStatus();
		
		if(articleStatusHistoryDTO.getCurrentStatus()==ArticleStatus.REJECTED)
			this.message=articleStatusHistoryDTO.getMessage();
	
		this.updatedAt=articleStatusHistoryDTO.getUpdatedAt();

		if(loadUser) {
			this.user=new User(articleStatusHistoryDTO.getUserDTO());
		}
		
		if(loadArticle) {
			this.article=new Article(articleStatusHistoryDTO.getArticleDTO());
		}
		
	}

}

