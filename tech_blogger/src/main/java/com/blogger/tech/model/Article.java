package com.blogger.tech.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
import lombok.Builder;
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
@Builder
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_SEQ")
  @SequenceGenerator(name = "ARTICLE_SEQ", sequenceName = "ARTICLE_SEQ", allocationSize = 1)
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
  @JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private List<Tag> tagList;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "collection_article", joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "collection_id"))
  private List<Collection> collectionList;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
  private List<ArticleStatusHistory> articleStatusHistoryList;

  public Article(ArticleDTO articleDTO) {

    this.id = articleDTO.getId();
    this.title = articleDTO.getTitle();
    this.content = articleDTO.getContent();
    this.status = articleDTO.getStatus();
    this.createdAt =
        articleDTO.getCreatedAt() == null ? LocalDateTime.now() : articleDTO.getCreatedAt();
    this.updatedAt = LocalDateTime.now();

  }

  @Override
  public int hashCode() {
    return Objects.hash(articleStatusHistoryList, collectionList, content, createdAt, id, status,
        tagList, title, updatedAt, user);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Article other = (Article) obj;
    return Objects.equals(articleStatusHistoryList, other.articleStatusHistoryList)
        && Objects.equals(collectionList, other.collectionList)
        && Objects.equals(content, other.content) && Objects.equals(createdAt, other.createdAt)
        && Objects.equals(id, other.id) && status == other.status
        && Objects.equals(tagList, other.tagList) && Objects.equals(title, other.title)
        && Objects.equals(updatedAt, other.updatedAt) && Objects.equals(user, other.user);
  }

}
