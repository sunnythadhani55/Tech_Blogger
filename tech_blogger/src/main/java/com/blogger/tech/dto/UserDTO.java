package com.blogger.tech.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.blogger.tech.enums.UserRoles;
import com.blogger.tech.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserDTO {

  private Long id;

  private String email;

  private String firstname;

  private String lastname;

  private String username;

  @JsonIgnore
  private String password;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  private UserRoles userRole;

  private List<TagDTO> tagDTOList;

  private List<ArticleDTO> articleDTOList;

  private List<CollectionDTO> collectionDTOList;


  public UserDTO(User user, Boolean loadTagDTOList, Boolean loadArticleDTOList,
      Boolean loadCollectionDTOList) {

    this.id = user.getId();
    this.email = user.getEmail();
    this.firstname = user.getFirstName();
    this.lastname = user.getLastName();
    this.username = user.getUsername();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
    this.userRole = user.getUserRole();

    if (loadTagDTOList) {
      this.tagDTOList.addAll(user.getTagList().stream().map(tag -> new TagDTO(tag, false, false))
          .collect(Collectors.toList()));
    }

    if (loadArticleDTOList) {
      this.articleDTOList.addAll(user.getArticleList().stream()
          .map(article -> new ArticleDTO(article, false, false, false))
          .collect(Collectors.toList()));
    }

    if (loadCollectionDTOList) {
      this.collectionDTOList.addAll(user.getCollectionList().stream()
          .map(collection -> new CollectionDTO(collection, false, false))
          .collect(Collectors.toList()));
    }

  }


  @Override
  public int hashCode() {
    return Objects.hash(articleDTOList, collectionDTOList, createdAt, email, firstname, id,
        lastname, password, tagDTOList, updatedAt, userRole, username);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserDTO other = (UserDTO) obj;
    return Objects.equals(articleDTOList, other.articleDTOList)
        && Objects.equals(collectionDTOList, other.collectionDTOList)
        && Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email)
        && Objects.equals(firstname, other.firstname) && Objects.equals(id, other.id)
        && Objects.equals(lastname, other.lastname) && Objects.equals(password, other.password)
        && Objects.equals(tagDTOList, other.tagDTOList)
        && Objects.equals(updatedAt, other.updatedAt) && userRole == other.userRole
        && Objects.equals(username, other.username);
  }

}
