package com.blogger.tech.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.blogger.tech.dto.TagDTO;

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
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_SEQ")
  @SequenceGenerator(name = "TAG_SEQ", sequenceName = "TAG_SEQ", allocationSize = 1)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "subscription", joinColumns = @JoinColumn(name = "tag_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> userList;

  @ManyToMany(mappedBy = "tagList")
  private List<Article> articleList;


  public Tag(TagDTO tagDTO, Boolean loadUserList, Boolean loadArticleList) {

    this.id = tagDTO.getId();
    this.name = tagDTO.getName();
    this.createdAt = tagDTO.getCreatedAt() == null ? LocalDateTime.now() : tagDTO.getCreatedAt();
    this.updatedAt = LocalDateTime.now();

  }


  @Override
  public int hashCode() {
    return Objects.hash(articleList, createdAt, id, name, updatedAt, userList);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Tag other = (Tag) obj;
    return Objects.equals(articleList, other.articleList)
        && Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
        && Objects.equals(name, other.name) && Objects.equals(updatedAt, other.updatedAt)
        && Objects.equals(userList, other.userList);
  }



}
