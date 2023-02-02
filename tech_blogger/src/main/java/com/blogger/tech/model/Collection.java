package com.blogger.tech.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
public class Collection {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLLECTION_SEQ")
  @SequenceGenerator(name = "COLLECTION_SEQ", sequenceName = "COLLECTION_SEQ", allocationSize = 1)
  private Long id;

  private String name;

  private String description;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "collection_article", joinColumns = @JoinColumn(name = "collection_id"),
      inverseJoinColumns = @JoinColumn(name = "article_id"))
  private List<Article> articleList;


  public Collection(CollectionDTO collectionDTO) {

    this.id = collectionDTO.getId();
    this.name = collectionDTO.getName();
    this.description = collectionDTO.getDescription();
    this.createdAt =
        collectionDTO.getCreatedAt() == null ? LocalDateTime.now() : collectionDTO.getCreatedAt();
    this.updatedAt = LocalDateTime.now();

  }


  @Override
  public int hashCode() {
    return Objects.hash(articleList, createdAt, description, id, name, updatedAt, user);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Collection other = (Collection) obj;
    return Objects.equals(articleList, other.articleList)
        && Objects.equals(createdAt, other.createdAt)
        && Objects.equals(description, other.description) && Objects.equals(id, other.id)
        && Objects.equals(name, other.name) && Objects.equals(updatedAt, other.updatedAt)
        && Objects.equals(user, other.user);
  }



}
