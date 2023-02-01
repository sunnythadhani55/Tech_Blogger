package com.blogger.tech.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import com.blogger.tech.model.Tag;
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
public class TagDTO {

  private Long id;

  @NotEmpty(message = "Tag Name must not be null or empty")
  private String name;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<UserDTO> userDTOList;

  private List<ArticleDTO> articleDTOList;


  public TagDTO(Tag tag, Boolean loadUserDTOList, Boolean loadArticleDTOList) {
    this.id = tag.getId();
    this.name = tag.getName();
    this.createdAt = tag.getCreatedAt();
    this.updatedAt = tag.getUpdatedAt();

    if (loadUserDTOList && tag.getUserList() != null) {
      this.userDTOList = tag.getUserList().stream().map(user -> new UserDTO(user, true, true, true))
          .collect(Collectors.toList());
    }

    if (loadArticleDTOList && tag.getArticleList() != null) {
      this.articleDTOList = tag.getArticleList().stream()
          .map(article -> new ArticleDTO(article, true, true, false)).collect(Collectors.toList());
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleDTOList, createdAt, id, name, updatedAt, userDTOList);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TagDTO other = (TagDTO) obj;
    return Objects.equals(articleDTOList, other.articleDTOList)
        && Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
        && Objects.equals(name, other.name) && Objects.equals(updatedAt, other.updatedAt)
        && Objects.equals(userDTOList, other.userDTOList);
  }

}
