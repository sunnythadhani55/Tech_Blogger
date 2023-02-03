package com.blogger.tech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.blogger.tech.dto.ArticleDTO;
import com.blogger.tech.dto.CollectionDTO;
import com.blogger.tech.dto.UserDTO;
import com.blogger.tech.enums.ArticleStatus;
import com.blogger.tech.enums.UserRole;
import com.blogger.tech.exception.ResourceNotFoundException;
import com.blogger.tech.exception.UnauthorizedException;
import com.blogger.tech.model.Article;
import com.blogger.tech.model.Collection;
import com.blogger.tech.model.User;
import com.blogger.tech.repository.ArticleRepository;
import com.blogger.tech.repository.CollectionRepository;
import com.blogger.tech.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CollectionServiceTest {

  @Mock
  private CollectionRepository collectionRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ArticleRepository articleRepository;

  @InjectMocks
  private CollectionServiceImpl collectionService;

  final private LocalDateTime timeStamp = LocalDateTime.now();

  private List<Collection> collectionList;

  private List<CollectionDTO> collectionDTOList;

  private List<Article> articleList;

  private List<ArticleDTO> articleDTOList;

  // creates test data before executing first test case
  @BeforeEach
  public void init() {

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


    // Creating test data as List of Collections
    collectionList = new ArrayList();

    collectionList.add(new Collection(1L, "Java Articles", "Articles related to Java", timeStamp,
        timeStamp, null, null));
    collectionList.add(new Collection(2L, "Python Articles", "Articles related to Python",
        timeStamp, timeStamp, null, null));
    collectionList.add(new Collection(3L, "REST Articles", "Articles related to REST APIs",
        timeStamp, timeStamp, null, null));
    collectionList.add(new Collection(4L, "AI Articles", "Articles related to AI", timeStamp,
        timeStamp, null, null));

    // Creating test data as List of Collection DTOs
    collectionDTOList = new ArrayList<>();

    collectionDTOList.add(new CollectionDTO(1L, "Java Articles", "Articles related to Java",
        timeStamp, timeStamp, null, null));
    collectionDTOList.add(new CollectionDTO(2L, "Python Articles", "Articles related to Python",
        timeStamp, timeStamp, null, null));
    collectionDTOList.add(new CollectionDTO(3L, "REST Articles", "Articles related to REST APIs",
        timeStamp, timeStamp, null, null));
    collectionDTOList.add(new CollectionDTO(4L, "AI Articles", "Articles related to AI", timeStamp,
        timeStamp, null, null));
  }

  // getAllByUserId method test cases
  @Test
  void should_find_and_return_all_Collections_for_user() {
    Long userId = 1L;

    User user = new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(collectionRepository.findByUser(user)).thenReturn(collectionList);

    List<CollectionDTO> actualCollectionDTOList = collectionService.getAllByUserId(userId);

    assertEquals(collectionDTOList.size(), actualCollectionDTOList.size());
    assertEquals(collectionDTOList, actualCollectionDTOList);
  }

  // FindById method test cases
  @Test
  void should_find_Collection_by_Id_and_return_Collection() {
    Long userId = 1L;
    Long collectionId = 1L;

    User user = new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    UserDTO userDTO = new UserDTO(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        null, timeStamp, timeStamp, UserRole.READER, null, null, null);

    List<Collection> collectionList = new ArrayList();

    collectionList.add(new Collection(1L, "Java Articles", "Articles related to Java", timeStamp,
        timeStamp, user, articleList));
    collectionList.add(new Collection(2L, "Python Articles", "Articles related to Python",
        timeStamp, timeStamp, user, articleList));
    collectionList.add(new Collection(3L, "REST Articles", "Articles related to REST APIs",
        timeStamp, timeStamp, user, articleList));
    collectionList.add(new Collection(4L, "AI Articles", "Articles related to AI", timeStamp,
        timeStamp, user, articleList));

    user.setCollectionList(collectionList);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(collectionRepository.existsById(collectionId)).thenReturn(true);

    when(collectionRepository.findByUser(user)).thenReturn(collectionList);

    CollectionDTO expectedCollectionDTO = new CollectionDTO(1L, "Java Articles",
        "Articles related to Java", timeStamp, timeStamp, userDTO, articleDTOList);
    CollectionDTO actualCollectionDTO = collectionService.getById(collectionId, userId);

    assertEquals(expectedCollectionDTO, actualCollectionDTO);
  }

  @Test
  void should_throw_ResourceNotFoundException_when_called_Collection_by_Id_method() {
    Long userId = 1L;
    Long collectionId = 5L;

    User user = new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(collectionRepository.existsById(collectionId)).thenReturn(false);

    Exception ex = assertThrows(ResourceNotFoundException.class,
        () -> collectionService.getById(collectionId, userId));
    assertEquals("Collection does not exists for Id 5", ex.getMessage());
  }

  @Test
  void should_throw_UnauthorizedException_when_called_Collection_by_Id_method() {
    Long userId = 1L;
    Long collectionId = 4L;

    User user = new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    UserDTO userDTO = new UserDTO(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        null, timeStamp, timeStamp, UserRole.READER, null, null, null);

    List<Collection> collectionList = new ArrayList();

    collectionList.add(new Collection(1L, "Java Articles", "Articles related to Java", timeStamp,
        timeStamp, null, articleList));
    collectionList.add(new Collection(2L, "Python Articles", "Articles related to Python",
        timeStamp, timeStamp, user, articleList));
    collectionList.add(new Collection(3L, "REST Articles", "Articles related to REST APIs",
        timeStamp, timeStamp, user, articleList));

    user.setCollectionList(collectionList);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(collectionRepository.existsById(collectionId)).thenReturn(true);

    when(collectionRepository.findByUser(user)).thenReturn(collectionList);

    Exception ex = assertThrows(UnauthorizedException.class,
        () -> collectionService.getById(collectionId, userId));
    assertEquals("User is unauthorized to  access Collection with Id 4", ex.getMessage());
  }
  
  // Test cases related to creating new Collection
  @Test
  void should_save_one_collection() {
    Long userId = 1L;

    CollectionDTO collectionDTO = CollectionDTO.builder().name("Core Java")
        .description("This collections contains core java related articles")
        .articleDTOList(articleDTOList).build();
    
    User user = new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    UserDTO userDTO=new UserDTO(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        null, timeStamp, timeStamp, UserRole.READER, null, null, null);
    
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    for (int i = 1; i <= articleList.size(); i++) {
      Long articleId = (long) i;
      when(articleRepository.findById(articleId)).thenReturn(Optional.of(articleList.get(i - 1)));
    }

    Collection savedCollection =
        new Collection(1L, "Core Java", "This collections contains core java related articles",
            timeStamp, timeStamp, user, articleList);
   
    when(collectionRepository.save(any(Collection.class)))
      .thenReturn(savedCollection);
    
    CollectionDTO returnedCollectionDTO=collectionService.add(collectionDTO, userId);
    
    assertEquals(collectionDTO.getName(), returnedCollectionDTO.getName());
    assertEquals(collectionDTO.getDescription(), returnedCollectionDTO.getDescription());
    assertEquals(timeStamp, returnedCollectionDTO.getCreatedAt());
    assertEquals(timeStamp, returnedCollectionDTO.getUpdatedAt());
    assertEquals(userDTO, returnedCollectionDTO.getUserDTO());
    assertEquals(articleDTOList, returnedCollectionDTO.getArticleDTOList());
  }
  
  @Test
  void should_throw_ResourceNotFoundException_when_called_add_method() {
    Long userId = 1L;
    Long inputArticleId=20L;

    articleDTOList.add(ArticleDTO.builder().id(inputArticleId).build());
    
    CollectionDTO collectionDTO = CollectionDTO.builder().name("Core Java")
        .description("This collections contains core java related articles")
        .articleDTOList(articleDTOList).build();
    
    User user = new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    UserDTO userDTO=new UserDTO(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        null, timeStamp, timeStamp, UserRole.READER, null, null, null);
    
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(articleRepository.findById(inputArticleId))
      .thenReturn(Optional.empty());
    
    for (int i = 1; i <= articleList.size(); i++) {
      Long articleId = (long) i;
      when(articleRepository.findById(articleId)).thenReturn(Optional.of(articleList.get(i - 1)));
    }
    
    Exception ex=assertThrows(ResourceNotFoundException.class, () -> collectionService.add(collectionDTO, userId));
    assertEquals("Article does not exists for Id 20", ex.getMessage());
    
    verify(collectionRepository,times(0)).save(any(Collection.class));
  }
  
  //Test cases related to update Collection
  @Test
  void should_update_collection() {
    Long userId = 1L;
    Long collectionId=1L;
    LocalDateTime updatedTimeStamp = LocalDateTime.now();
    
    CollectionDTO collectionDTO = CollectionDTO.builder().id(collectionId).name("Advanced Java")
        .description("This collections contains advanced java related articles")
        .articleDTOList(articleDTOList).build();
    
    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    UserDTO userDTO=new UserDTO(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        null, timeStamp, timeStamp, UserRole.READER, null, null, null);
    
    Collection existingCollection= new Collection(collectionId, "Core Java", "This collections contains core java related articles",
        timeStamp, timeStamp, user, articleList);
    
    Collection updatedCollection= new Collection(collectionId, "Advanced Java", "This collections contains advanced java related articles",
        timeStamp, updatedTimeStamp, user, articleList);
    
    when(collectionRepository.findById(collectionId))
      .thenReturn(Optional.of(existingCollection));
    
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    
    for (int i = 1; i <= articleList.size(); i++) {
      Long articleId = (long) i;
      when(articleRepository.findById(articleId)).thenReturn(Optional.of(articleList.get(i - 1)));
    }
    
    when(collectionRepository.save(any(Collection.class)))
    .thenReturn(updatedCollection);
  
    CollectionDTO returnedCollectionDTO=collectionService.update(collectionDTO, userId);
    
    assertEquals(collectionDTO.getName(), returnedCollectionDTO.getName());
    assertEquals(collectionDTO.getDescription(), returnedCollectionDTO.getDescription());
    assertEquals(timeStamp, returnedCollectionDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedCollectionDTO.getUpdatedAt());
    assertEquals(userDTO, returnedCollectionDTO.getUserDTO());
    assertEquals(articleDTOList, returnedCollectionDTO.getArticleDTOList());
  }
  
  @Test
  void should_throw_ResourceNotFoundException_for_CollectionId_when_called_update_method() {
    Long userId = 1L;
    Long collectionId=50L;
    
    CollectionDTO collectionDTO = CollectionDTO.builder().id(collectionId).name("Core Java")
        .description("This collections contains core java related articles")
        .articleDTOList(articleDTOList).build();
    
    when(collectionRepository.findById(collectionId))
      .thenReturn(Optional.empty());
    
    Exception ex=assertThrows(ResourceNotFoundException.class, () -> collectionService.update(collectionDTO, userId));
    assertEquals("Collection does not exists for Id 50", ex.getMessage());
    
    verify(articleRepository,times(0)).findById(anyLong());
    verify(collectionRepository,times(0)).save(any(Collection.class));
  }

  @Test
  void should_throw_ResourceNotFoundException_for_ArticleId_when_called_update_method() {
    Long userId = 1L;
    Long collectionId=1L;
    Long inputArticleId=20L;

    articleDTOList.add(ArticleDTO.builder().id(inputArticleId).build());
    
    CollectionDTO collectionDTO = CollectionDTO.builder().id(collectionId).name("Advanced Java")
        .description("This collections contains advanced java related articles")
        .articleDTOList(articleDTOList).build();
    
    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    UserDTO userDTO=new UserDTO(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        null, timeStamp, timeStamp, UserRole.READER, null, null, null);
    
    Collection existingCollection= new Collection(collectionId, "Core Java", "This collections contains core java related articles",
        timeStamp, timeStamp, user, articleList);
    
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(articleRepository.findById(inputArticleId))
      .thenReturn(Optional.empty());
    
    when(collectionRepository.findById(collectionId))
      .thenReturn(Optional.of(existingCollection));
    
    for (int i = 1; i <= articleList.size(); i++) {
      Long articleId = (long) i;
      when(articleRepository.findById(articleId)).thenReturn(Optional.of(articleList.get(i - 1)));
    }
    
    Exception ex=assertThrows(ResourceNotFoundException.class, () -> collectionService.update(collectionDTO, userId));
    assertEquals("Article does not exists for Id 20", ex.getMessage());
    
    verify(collectionRepository,times(0)).save(any(Collection.class));
  }
  
  //Test cases related to deleting Collection By Id
  @Test
  void deleteCollectionById_ValidInput_ShouldDeleteArticle() {
    Long userId = 1L;
    Long collectionId=1L;
    
    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    when(userRepository.findById(userId))
      .thenReturn(Optional.of(user));
    
    when(collectionRepository.findByUser(user))
      .thenReturn(collectionList);
    
    collectionService.deleteById(collectionId, userId);
    
    verify(collectionRepository,times(1)).deleteById(collectionId);
  }
  
  @Test
  void deleteCollectionById_InValidInput_Should_throw_UnauthorizedException() {
    Long userId = 1L;
    Long collectionId=20L;
    
    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    when(userRepository.findById(userId))
      .thenReturn(Optional.of(user));
    
    when(collectionRepository.findByUser(user))
      .thenReturn(collectionList);
    
    Exception ex= assertThrows(UnauthorizedException.class, () -> collectionService.deleteById(collectionId, userId));
    assertEquals("User is unauthorized to  access Collection with Id 20", ex.getMessage());
    
    verify(collectionRepository,times(0)).deleteById(collectionId);
  }
}
