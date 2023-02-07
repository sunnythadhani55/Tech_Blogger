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
import com.blogger.tech.enums.UserRole;
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
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.WRITER, null, null, null));
    userList.add(new User(2L, "ankitgupta@gmail.com", "Ankit", "Gupta", "Ankit123",
        "usdasddfsdasdda", timeStamp, timeStamp, UserRole.ADMIN, null, null, null));
    userList.add(new User(3L, "vishalsharma@gmail.com", "Vishal", "Sharma", "Vishal123",
        "oioioioioiohhjkh", timeStamp, timeStamp, UserRole.READER, null, null, null));

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
  void testGetAll_Return_All_Articles() {
    when(articleRepository.findAll()).thenReturn(articleList);
    List<ArticleDTO> actualArticleDTOList = articleService.getAll();
    assertEquals(4, actualArticleDTOList.size());
    assertEquals(articleDTOList, actualArticleDTOList);
  }

  // FindById method test cases
  @Test
  void testGetById_When_KeyIdExists_Then_Return_Article() {
    Long articleId = 1L;
    when(articleRepository.findById(articleId)).thenReturn(Optional.of(articleList.get(0)));
    ArticleDTO actualArticleDTO = articleService.getById(articleId);
    assertEquals(articleDTOList.get(0), actualArticleDTO);
  }

  @Test
  void testGetById_When_KeyId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {
    Long articleId = 20L;
    when(articleRepository.findById(articleId)).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFoundException.class,
        () -> articleService.getById(articleId), "Article");
    assertEquals("Article does not exists for Id 20", exception.getMessage());
  }

  // Test cases related to creating new Article
  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void testAdd_Create_Article_And_Article_History() {

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

  // Test cases related to update Article
  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void testUpdate_When_KeyIdExists_Status_From_Draft_To_Draft_Then_Update_Article_But_Not_Create_ArticleStatusHistory() {

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
  void testUpdate_When_KeyIdExists_Status_From_Draft_To_UnderReview_Then_Update_Article_And_Create_ArticleStatusHistory() {

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
  void testUpdate_When_KeyIdExists_Status_From_Draft_To_Published_Then_Throw_IllegalStatusException() {

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
  void testUpdate_When_KeyIdExists_Status_From_Draft_To_Rejected_Then_Throw_IllegalStatusException() {

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
  void testUpdate_When_KeyIdExists_Status_From_UnderReview_To_Draft_Then_Update_Article_And_Create_ArticleStatusHistory() {

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
  void testUpdate_When_KeyIdExists_Status_From_UnderReview_To_UnderReview_Then_Update_Article_But_Not_Create_ArticleStatusHistory() {

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
  void testUpdate_When_KeyIdExists_Status_From_UnderReview_To_Published_Then_Throw_IllegalStatusException() {

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
  void testUpdate_When_KeyIdExists_Status_From_UnderReview_To_Rejected_Then_Throw_IllegalStatusException() {

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
  void testUpdate_When_KeyIdExists_Status_From_Rejected_To_Draft_Then_Update_Article_And_Create_ArticleStatusHistory() {

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
  void testUpdate_When_KeyIdExists_Status_From_Rejected_To_UnderReview_Then_Update_Article_And_Create_ArticleStatusHistory() {

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
  void testUpdate_When_KeyIdExists_Status_From_Rejected_To_Rejected_Then_Update_Article_But_Not_Create_ArticleStatusHistory() {

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
  void testUpdate_When_KeyIdExists_Status_From_Rejected_To_Published_Then_Throw_IllegalStatusException() {

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
  void testUpdate_When_KeyId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {

    Long articleId = 50L;
    when(articleRepository.findById(articleId)).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFoundException.class,
        () -> articleService.getById(articleId), "Article");
    assertEquals("Article does not exists for Id 50", exception.getMessage());
  }

  // Test cases related to deleting Article By Id
  @Test
  void testDeleteById_When_KeyIdExists_Then_Delete_Article() {
    Long id = 5L;
    when(articleRepository.existsById(id)).thenReturn(true);
    articleService.deleteById(id);
    verify(articleRepository).deleteById(id);
  }

  @Test
  void testDeleteById_When_KeyId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {
    Long id = 50L;
    when(articleRepository.existsById(id)).thenReturn(false);

    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> articleService.deleteById(id));
    assertEquals("Article does not exists for Id 50", exception.getMessage());
  }

  @Test
  void testGetAllByUser_Return_All_Articles_Wrtitten_By_User() {
    Long userId = 1L;

    List<Article> inputArticleList = Arrays.asList(
        new Article(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp, timeStamp, null,
            null, null, null),
        new Article(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp, timeStamp, null,
            null, null, null));

    User user = User.builder().id(1L).email("sunnythadhani87@gmail.com").firstName("Sunny")
        .lastName("Thadhani").username("Sunny123").password("dhaskhdilldalskk").createdAt(timeStamp)
        .updatedAt(timeStamp).userRole(UserRole.WRITER).articleList(inputArticleList).build();

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
  void testGetPublishedArticlesByTag_When_KeyTagIdExists_Return_All_Articles_By_Tag() {
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
  void testGetPublishedArticlesByTag_When_KeyTagId_Does_Not_Exists_Throw_ResourceNotFoundException() {
    Long tagId = 50L;
    when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class,
        () -> articleService.getPublishedArticlesByTag(tagId));
    assertEquals("Tag does not exists for Id 50", exception.getMessage());
  }

  // Test cases related to updating status by admin
  @Test
  void testUpdateStatus_When_KeyTagIdExists_Status_From_UnderReview_To_Publsihed_Update_Status_Create_ArticleStatusHistory() {

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
  void testUpdateStatus_When_KeyTagIdExists_Status_From_UnderReview_To_Rejected_Update_Status_Create_ArticleStatusHistory() {
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
  void testUpdateStatus_When_KeyTagId_Does_Not_Exists_Throw_ResourceNotFoundException() {
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
