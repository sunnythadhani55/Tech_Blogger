package com.blogger.tech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.blogger.tech.dto.ArticleDTO;
import com.blogger.tech.dto.ArticleStatusHistoryDTO;
import com.blogger.tech.dto.TagDTO;
import com.blogger.tech.dto.UserDTO;
import com.blogger.tech.enums.ArticleStatus;
import com.blogger.tech.enums.UserRoles;
import com.blogger.tech.exception.IllegalStatusException;
import com.blogger.tech.exception.ResourceNotFoundException;
import com.blogger.tech.model.Article;
import com.blogger.tech.model.ArticleStatusHistory;
import com.blogger.tech.model.Tag;
import com.blogger.tech.model.User;
import com.blogger.tech.repository.ArticleRepository;
import com.blogger.tech.repository.ArticleStatusHistoryRepoistory;
import com.blogger.tech.repository.TagRepository;
import com.blogger.tech.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

  @Mock
  private ArticleRepository articleRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TagRepository tagRepository;

  @Mock
  private ArticleStatusHistoryRepoistory articleStatusHistoryRepoistory;

  @InjectMocks
  private ArticleServiceImpl articleService;

  private List<Article> articleList;

  private List<ArticleDTO> articleDTOList;

  private List<User> userList;

  private List<UserDTO> userDTOList;

  private List<Tag> tagList;

  private List<TagDTO> tagDTOList;

  final private LocalDateTime timeStamp = LocalDateTime.now();

  // creates test data before executing first test case
  @BeforeEach
  public void init() {

    // Creating test data as List of Tags
    tagList = new ArrayList<>();

    tagList.add(new Tag(1L, "Youtube", timeStamp, timeStamp, null, null));
    tagList.add(new Tag(2L, "Twitter", timeStamp, timeStamp, null, null));
    tagList.add(new Tag(3L, "Artifical Intelligence", timeStamp, timeStamp, null, null));
    tagList.add(new Tag(4L, "IOT", timeStamp, timeStamp, null, null));
    tagList.add(new Tag(5L, "Blockchain", timeStamp, timeStamp, null, null));

    // Creating test data as List of TagDTOs
    tagDTOList = new ArrayList<>();

    tagDTOList.add(new TagDTO(1L, "Youtube", timeStamp, timeStamp, null, null));
    tagDTOList.add(new TagDTO(2L, "Twitter", timeStamp, timeStamp, null, null));
    tagDTOList.add(new TagDTO(3L, "Artifical Intelligence", timeStamp, timeStamp, null, null));
    tagDTOList.add(new TagDTO(4L, "IOT", timeStamp, timeStamp, null, null));
    tagDTOList.add(new TagDTO(5L, "Blockchain", timeStamp, timeStamp, null, null));

    // Creating test data as List of Users
    userList = new ArrayList<>();

    userList.add(new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRoles.WRITER, null, null, null));
    userList.add(new User(2L, "ankitgupta@gmail.com", "Ankit", "Gupta", "Ankit123",
        "usdasddfsdasdda", timeStamp, timeStamp, UserRoles.ADMIN, null, null, null));
    userList.add(new User(3L, "vishalsharma@gmail.com", "Vishal", "Sharma", "Vishal123",
        "oioioioioiohhjkh", timeStamp, timeStamp, UserRoles.READER, null, null, null));

    // Creating test data as List of User DTOs
    userDTOList = new ArrayList<>();

    userDTOList.add(new UserDTO(userList.get(0), false, false, false));
    userDTOList.add(new UserDTO(userList.get(1), false, false, false));
    userDTOList.add(new UserDTO(userList.get(2), false, false, false));


    // Creating test data as List of Articles
    articleList = new ArrayList<>();

    articleList.add(new Article(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp,
        timeStamp, null, null, null, null));
    articleList.add(new Article(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp,
        timeStamp, null, null, null, null));
    articleList.add(new Article(3L, "Title 3", "Content 3", ArticleStatus.PUBLISHED, timeStamp,
        timeStamp, null, null, null, null));
    articleList.add(new Article(4L, "Title 4", "Content 4", ArticleStatus.DRAFT, timeStamp,
        timeStamp, null, null, null, null));


    // Creating test data as List of Article DTOs
    articleDTOList = new ArrayList<>();

    articleDTOList.add(new ArticleDTO(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED,
        timeStamp, timeStamp, null, null, null, null));
    articleDTOList.add(new ArticleDTO(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp,
        timeStamp, null, null, null, null));
    articleDTOList.add(new ArticleDTO(3L, "Title 3", "Content 3", ArticleStatus.PUBLISHED,
        timeStamp, timeStamp, null, null, null, null));
    articleDTOList.add(new ArticleDTO(4L, "Title 4", "Content 4", ArticleStatus.DRAFT, timeStamp,
        timeStamp, null, null, null, null));


  }

  // FindAll method test cases
  @Test
  void should_find_and_return_all_Articles() {
    when(articleRepository.findAll()).thenReturn(articleList);
    List<ArticleDTO> actualArticleDTOList = articleService.getAll();
    assertEquals(4, actualArticleDTOList.size());
    assertEquals(articleDTOList, actualArticleDTOList);
  }

  // FindById method test cases
  @Test
  void should_find_article_by_Id_and_return_article() {
    Long articleId = 1L;
    when(articleRepository.findById(articleId)).thenReturn(Optional.of(articleList.get(0)));
    ArticleDTO actualArticleDTO = articleService.getById(articleId);
    assertEquals(articleDTOList.get(0), actualArticleDTO);
  }

  @Test
  void should_throw_ResourceNotFoundException_when_find_article_by_id() {
    Long articleId = 20L;
    when(articleRepository.findById(articleId)).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFoundException.class,
        () -> articleService.getById(articleId), "Article");
    assertEquals("Article does not exists for Id 20", exception.getMessage());
  }

  // Test cases related to creating new Article
  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_save_one_Article_and_create_status_history() {

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO =
        ArticleDTO.builder().title("Title 1").content("Content 1").status(ArticleStatus.PUBLISHED)
            .tagDTOList(inputTagDTOList).userDTO(userDTOList.get(0)).build();

    Article savedArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.PUBLISHED).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(savedArticle);


    ArticleDTO returnedArticleDTO = articleService.add(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(timeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.PUBLISHED, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(1)).save(any(ArticleStatusHistory.class));
  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_update_Article_and_not_create_ArticleStatusHistory_DRAFT_to_DRAFT() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.DRAFT).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.DRAFT).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.DRAFT).createdAt(timeStamp).updatedAt(timeStamp).tagList(inputTagList)
        .user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    ArticleDTO returnedArticleDTO = articleService.update(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.DRAFT, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(0)).save(any(ArticleStatusHistory.class));

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_update_Article_and_create_ArticleStatusHistory_DRAFT_to_UNDER_REVIEW() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.UNDER_REVIEW).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.UNDER_REVIEW).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.DRAFT).createdAt(timeStamp).updatedAt(timeStamp).tagList(inputTagList)
        .user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    ArticleDTO returnedArticleDTO = articleService.update(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.UNDER_REVIEW, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(1)).save(any(ArticleStatusHistory.class));

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_throw_IllegalStatusException_DRAFT_to_PUBLISHED() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.PUBLISHED).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.PUBLISHED).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.DRAFT).createdAt(timeStamp).updatedAt(timeStamp).tagList(inputTagList)
        .user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    Exception ex =
        assertThrows(IllegalStatusException.class, () -> articleService.update(articleDTO, userId));
    assertEquals("Can not update status from DRAFT to PUBLISHED", ex.getMessage());

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_throw_IllegalStatusException_DRAFT_to_REJECTED() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.REJECTED).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.REJECTED).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.DRAFT).createdAt(timeStamp).updatedAt(timeStamp).tagList(inputTagList)
        .user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    Exception ex =
        assertThrows(IllegalStatusException.class, () -> articleService.update(articleDTO, userId));
    assertEquals("Can not update status from DRAFT to REJECTED", ex.getMessage());

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_update_Article_and_create_ArticleStatusHistory_UNDER_REVIEW_to_DRAFT() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.DRAFT).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.DRAFT).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.UNDER_REVIEW).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    ArticleDTO returnedArticleDTO = articleService.update(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.DRAFT, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(1)).save(any(ArticleStatusHistory.class));

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_update_Article_and_not_create_ArticleStatusHistory_UNDER_REVIEW_to_UNDER_REVIEW() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.UNDER_REVIEW).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.UNDER_REVIEW).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.UNDER_REVIEW).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    ArticleDTO returnedArticleDTO = articleService.update(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.UNDER_REVIEW, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(0)).save(any(ArticleStatusHistory.class));

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_throw_IllegalStatusException_UNDER_REVIEW_to_PUBLISHED() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.PUBLISHED).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.PUBLISHED).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.UNDER_REVIEW).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    Exception ex =
        assertThrows(IllegalStatusException.class, () -> articleService.update(articleDTO, userId));
    assertEquals("Can not update status from UNDER_REVIEW to PUBLISHED", ex.getMessage());

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_throw_IllegalStatusException_UNDER_REVIEW_to_REJECTED() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.REJECTED).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.REJECTED).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.UNDER_REVIEW).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    Exception ex =
        assertThrows(IllegalStatusException.class, () -> articleService.update(articleDTO, userId));
    assertEquals("Can not update status from UNDER_REVIEW to REJECTED", ex.getMessage());

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_update_Article_and_create_ArticleStatusHistory_REJECTED_to_DRAFT() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.DRAFT).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.DRAFT).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.REJECTED).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    ArticleDTO returnedArticleDTO = articleService.update(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.DRAFT, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(1)).save(any(ArticleStatusHistory.class));

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_update_Article_and_create_ArticleStatusHistory_REJECTED_to_UNDER_REVIEW() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.UNDER_REVIEW).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.UNDER_REVIEW).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.REJECTED).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    ArticleDTO returnedArticleDTO = articleService.update(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.UNDER_REVIEW, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(1)).save(any(ArticleStatusHistory.class));

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_update_Article_and_not_create_ArticleStatusHistory_REJECTED_to_REJECTED() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.REJECTED).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.REJECTED).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.REJECTED).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    ArticleDTO returnedArticleDTO = articleService.update(articleDTO, userId);

    List<TagDTO> expectedTagDTOList = new ArrayList<>();
    expectedTagDTOList.add(tagDTOList.get(2));
    expectedTagDTOList.add(tagDTOList.get(4));

    assertEquals(articleDTO.getTitle(), returnedArticleDTO.getTitle());
    assertEquals(articleDTO.getContent(), returnedArticleDTO.getContent());
    assertEquals(timeStamp, returnedArticleDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedArticleDTO.getUpdatedAt());
    assertEquals(ArticleStatus.REJECTED, returnedArticleDTO.getStatus());
    assertEquals(userDTOList.get(0), returnedArticleDTO.getUserDTO());
    assertEquals(expectedTagDTOList, returnedArticleDTO.getTagDTOList());

    verify(articleStatusHistoryRepoistory, times(0)).save(any(ArticleStatusHistory.class));

  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void should_throw_IllegalStatusException_REJECTED_to_PUBLISHED() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    List<TagDTO> inputTagDTOList = Arrays.asList(new TagDTO(3L, null, null, null, null, null),
        new TagDTO(5L, null, null, null, null, null));

    List<Tag> inputTagList = new ArrayList<>();
    inputTagList.add(tagList.get(2));
    inputTagList.add(tagList.get(4));

    ArticleDTO articleDTO = ArticleDTO.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.PUBLISHED).tagDTOList(inputTagDTOList)
        .userDTO(userDTOList.get(0)).build();


    Article updatedArticle = Article.builder().id(1L).title("Updated Title 1")
        .content("Updated Content 1").status(ArticleStatus.PUBLISHED).createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).tagList(inputTagList).user(userList.get(0)).build();

    Article existingArticle = Article.builder().id(1L).title("Title 1").content("Content 1")
        .status(ArticleStatus.REJECTED).createdAt(timeStamp).updatedAt(timeStamp)
        .tagList(inputTagList).user(userList.get(0)).build();

    when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    for (int i = 1; i <= tagList.size(); i++) {
      Long tagId = (long) i;
      when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagList.get(i - 1)));
    }

    when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

    Exception ex =
        assertThrows(IllegalStatusException.class, () -> articleService.update(articleDTO, userId));
    assertEquals("Can not update status from REJECTED to PUBLISHED", ex.getMessage());

  }

  @Test
  void should_throw_ResourceNotFoundException_when_called_update_method() {

    Long articleId = 50L;
    when(articleRepository.findById(articleId)).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFoundException.class,
        () -> articleService.getById(articleId), "Article");
    assertEquals("Article does not exists for Id 50", exception.getMessage());
  }

  // Test cases related to deleting Article By Id
  @Test
  void deleteArticleById_ValidInput_ShouldDeleteArticle() {
    Long id = 5L;
    when(articleRepository.existsById(id)).thenReturn(true);
    articleService.deleteById(id);
    verify(articleRepository).deleteById(id);
  }

  @Test
  void deleteArticleById_InValidInput_Shouldthrow_ResourceNotFoundException() {
    Long id = 50L;
    when(articleRepository.existsById(id)).thenReturn(false);

    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> articleService.deleteById(id));
    assertEquals("Article does not exists for Id 50", exception.getMessage());
  }

  @Test
  void should_return_all_articles_written_by_user() {
    Long userId = 1L;

    List<Article> inputArticleList = Arrays.asList(
        new Article(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp, timeStamp, null,
            null, null, null),
        new Article(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp, timeStamp, null,
            null, null, null));

    User user = User.builder().id(1L).email("sunnythadhani87@gmail.com").firstName("Sunny")
        .lastName("Thadhani").username("Sunny123").password("dhaskhdilldalskk").createdAt(timeStamp)
        .updatedAt(timeStamp).userRole(UserRoles.WRITER).articleList(inputArticleList).build();

    List<ArticleDTO> expectedArticleDTOList = Arrays.asList(
        new ArticleDTO(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp, timeStamp,
            null, null, null, null),
        new ArticleDTO(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp, timeStamp, null,
            null, null, null));

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    List<ArticleDTO> actualArticleDTOList = articleService.getAllByUser(userId);

    assertEquals(expectedArticleDTOList.size(), actualArticleDTOList.size());
    assertEquals(expectedArticleDTOList, actualArticleDTOList);

  }

  @Test
  void should_return_published_articles_by_article() {
    Long tagId = 1L;

    List<Article> inputArticleList = Arrays.asList(
        new Article(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp, timeStamp, null,
            null, null, null),
        new Article(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp, timeStamp, null,
            null, null, null),
        new Article(3L, "Title 3", "Content 3", ArticleStatus.PUBLISHED, timeStamp, timeStamp, null,
            null, null, null),
        new Article(4L, "Title 4", "Content 4", ArticleStatus.PUBLISHED, timeStamp, timeStamp, null,
            null, null, null));

    Tag tag = Tag.builder().id(1L).name("Youtube").createdAt(timeStamp).updatedAt(timeStamp)
        .articleList(inputArticleList).build();

    List<ArticleDTO> expectedArticleDTOList = Arrays.asList(
        new ArticleDTO(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp, timeStamp,
            null, null, null, null),
        new ArticleDTO(3L, "Title 3", "Content 3", ArticleStatus.PUBLISHED, timeStamp, timeStamp,
            null, null, null, null),
        new ArticleDTO(4L, "Title 4", "Content 4", ArticleStatus.PUBLISHED, timeStamp, timeStamp,
            null, null, null, null));

    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    List<ArticleDTO> actualArticleDTOList = articleService.getPublishedArticlesByTag(tagId);

    assertEquals(expectedArticleDTOList.size(), actualArticleDTOList.size());
    assertEquals(expectedArticleDTOList, actualArticleDTOList);
  }

  @Test
  void should_throw_ResourceNotFoundException_when_called_getPublishedArticlesByTag_method() {
    Long tagId = 50L;
    when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class,
        () -> articleService.getPublishedArticlesByTag(tagId));
    assertEquals("Tag does not exists for Id 50", exception.getMessage());
  }

  // @Override
  // public void updateStatus(ArticleStatusHistoryDTO articleStatusHistoryDTO, Long userId) {
  //
  // Long articleId = articleStatusHistoryDTO.getArticleDTO().getId();
  //
  // Article article = articleRepository.findById(articleId)
  // .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", articleId.toString()));
  //
  //
  // switch (article.getStatus()) {
  // case UNDER_REVIEW:
  // switch (articleStatusHistoryDTO.getCurrentStatus()) {
  // case REJECTED, PUBLISHED:
  // articleRepository.updateStatus(articleStatusHistoryDTO.getCurrentStatus(), articleId);
  // article.setStatus(articleStatusHistoryDTO.getCurrentStatus());
  // article.setUpdatedAt(LocalDateTime.now());
  // articleStatusHistoryDTO = new ArticleStatusHistoryDTO(article, true, true);
  // createStatusHistory(articleStatusHistoryDTO);
  // break;
  // }
  // break;
  // }
  // }
  //

  // Test cases related to updating status by admin
  @Test
  void should_update_the_status_and_create_ArticleStatusHistory_UDNER_REVIEW_to_PUBLISHED() {

    Long userId = 1L;

    ArticleDTO articleDTO = ArticleDTO.builder().id(5L).build();

    Article article = Article.builder().id(5L).title("Title 5").content("Content 5")
        .createdAt(timeStamp).updatedAt(timeStamp).status(ArticleStatus.UNDER_REVIEW).build();

    ArticleStatusHistoryDTO articleStatusHistoryDTO =
        ArticleStatusHistoryDTO.builder().currentStatus(ArticleStatus.PUBLISHED)
            .userDTO(userDTOList.get(0)).articleDTO(articleDTO).build();

    when(articleRepository.findById(5L)).thenReturn(Optional.of(article));

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    articleService.updateStatus(articleStatusHistoryDTO, userId);

    verify(articleRepository, times(1)).updateStatus(articleStatusHistoryDTO.getCurrentStatus(),
        article.getId());
    verify(articleStatusHistoryRepoistory, times(1)).save(any(ArticleStatusHistory.class));
  }

  @Test
  void should_update_the_status_and_create_ArticleStatusHistory_UDNER_REVIEW_to_REJECTED() {
    Long userId = 1L;

    ArticleDTO articleDTO = ArticleDTO.builder().id(5L).build();

    Article article = Article.builder().id(5L).title("Title 5").content("Content 5")
        .createdAt(timeStamp).updatedAt(timeStamp).status(ArticleStatus.UNDER_REVIEW).build();

    ArticleStatusHistoryDTO articleStatusHistoryDTO = ArticleStatusHistoryDTO.builder()
        .currentStatus(ArticleStatus.REJECTED).message("Article Content is not relavent")
        .userDTO(userDTOList.get(0)).articleDTO(articleDTO).build();

    when(articleRepository.findById(5L)).thenReturn(Optional.of(article));

    when(userRepository.findById(userId)).thenReturn(Optional.of(userList.get(0)));

    articleService.updateStatus(articleStatusHistoryDTO, userId);

    verify(articleRepository, times(1)).updateStatus(articleStatusHistoryDTO.getCurrentStatus(),
        article.getId());
    verify(articleStatusHistoryRepoistory, times(1)).save(any(ArticleStatusHistory.class));
  }

  @Test
  void should_throw_ResourceNotFoundException_for_updateStatus_method() {
    Long userId = 1L;

    ArticleDTO articleDTO = ArticleDTO.builder().id(5L).build();

    ArticleStatusHistoryDTO articleStatusHistoryDTO = ArticleStatusHistoryDTO.builder()
        .currentStatus(ArticleStatus.REJECTED).message("Article Content is not relavent")
        .userDTO(userDTOList.get(0)).articleDTO(articleDTO).build();

    when(articleRepository.findById(5L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class,
        () -> articleService.updateStatus(articleStatusHistoryDTO, userId));
    assertEquals("Article does not exists for Id 5", exception.getMessage());
  }
}
