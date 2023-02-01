package com.blogger.tech.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.blogger.tech.enums.ArticleStatus;
import com.blogger.tech.model.Article;
import com.blogger.tech.model.ArticleStatusHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleStatusHistoryDTO {

  private Long id;

  @Enumerated(EnumType.STRING)
  private ArticleStatus currentStatus;

  private String message;

  private LocalDateTime updatedAt;

  private ArticleDTO articleDTO;

  private UserDTO userDTO;


  public ArticleStatusHistoryDTO(ArticleStatusHistory articleStatusHistory, Boolean loadUserDTO) {

    this.id = articleStatusHistory.getId();
    this.currentStatus = articleStatusHistory.getCurrentStatus();
    this.message = articleStatusHistory.getMessage();
    this.updatedAt = articleStatusHistory.getUpdatedAt();

    if (loadUserDTO) {
      this.userDTO = new UserDTO(articleStatusHistory.getUser(), false, false, false);
    }
  }

  public ArticleStatusHistoryDTO(Article article, Boolean loadUserDTO, Boolean loadArticleDTO) {

    this.currentStatus = article.getStatus();
    this.updatedAt = article.getUpdatedAt();

    if (loadUserDTO && article.getUser() != null) {
      this.userDTO = new UserDTO(article.getUser(), false, false, false);
    }

    if (loadArticleDTO && article != null) {
      this.articleDTO = new ArticleDTO(article, false, false, false);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleDTO, currentStatus, id, message, updatedAt, userDTO);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ArticleStatusHistoryDTO other = (ArticleStatusHistoryDTO) obj;
    return Objects.equals(articleDTO, other.articleDTO) && currentStatus == other.currentStatus
        && Objects.equals(id, other.id) && Objects.equals(message, other.message)
        && Objects.equals(updatedAt, other.updatedAt) && Objects.equals(userDTO, other.userDTO);
  }



}
