package com.blogger.tech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.blogger.tech.dto.TagDTO;
import com.blogger.tech.enums.UserRole;
import com.blogger.tech.exception.ResourceAlreadyExistsException;
import com.blogger.tech.exception.ResourceNotFoundException;
import com.blogger.tech.model.Tag;
import com.blogger.tech.model.User;
import com.blogger.tech.repository.TagRepository;
import com.blogger.tech.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

  @Mock
  private TagRepository tagRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  TagServiceImpl tagService;

  private List<Tag> tagList;

  private List<TagDTO> tagDTOList;

  private List<User> userList;

  final private LocalDateTime timeStamp = LocalDateTime.now();

  // creates test data before executing first test case
  @BeforeEach
  public void init() {

    // Creating test data as List of Users
    userList = new ArrayList<>();

    userList.add(new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.WRITER, null, null, null));
    userList.add(new User(2L, "ankitgupta@gmail.com", "Ankit", "Gupta", "Ankit123",
        "usdasddfsdasdda", timeStamp, timeStamp, UserRole.ADMIN, null, null, null));
    userList.add(new User(3L, "vishalsharma@gmail.com", "Vishal", "Sharma", "Vishal123",
        "oioioioioiohhjkh", timeStamp, timeStamp, UserRole.READER, null, null, null));

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
  }

  // FindAll method test cases
  // testCreateTag_When_KeyNameExists_Then_ThrowsDuplicateKeyNameException
  @Test
  void testGetAll_Return_All_Tags() {
    when(tagRepository.findAll()).thenReturn(tagList);
    List<TagDTO> actualTagDTOList = tagService.getAll();
    assertEquals(5, actualTagDTOList.size());
    assertEquals(tagDTOList, actualTagDTOList);
  }

  // Find By Id method test cases
  @Test
  void testGetById_When_KeyIdExists_Then_Return_Tag() {
    when(tagRepository.findById(1L)).thenReturn(Optional.of(tagList.get(0)));
    TagDTO actualTagDTO = tagService.getById(1L);
    assertEquals(tagDTOList.get(0), actualTagDTO);
  }

  @Test
  void testGetById_When_KeyId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {
    when(tagRepository.findById(20L)).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> tagService.getById(20L), "Tag");
    assertEquals("Tag does not exists for Id 20", exception.getMessage());
  }

  // Test cases related to creating new tag
  @Test
  void testAdd_When_KeyName_Unique_Then_Create_Tag() {

    TagDTO tagDTO = TagDTO.builder().name("Mech").build();

    Tag savedTag =
        Tag.builder().id(1L).name("Mech").createdAt(timeStamp).updatedAt(timeStamp).build();

    when(tagRepository.existsByName("Mech")).thenReturn(false);
    when(tagRepository.save(any(Tag.class))).thenReturn(savedTag);

    TagDTO returnedTagDTO = tagService.add(tagDTO);

    assertEquals(tagDTO.getName(), returnedTagDTO.getName());
    assertEquals(1L, returnedTagDTO.getId());
    assertEquals(timeStamp, returnedTagDTO.getCreatedAt());
    assertEquals(timeStamp, returnedTagDTO.getUpdatedAt());
  }

  @Test
  void testAdd_When_Unique_KeyNameExists_Then_Throw_ResourceAlreadyExistsException() {
    when(tagRepository.existsByName("Youtube")).thenReturn(true);

    TagDTO tagDTO = TagDTO.builder().name("Youtube").build();

    Exception exception =
        assertThrows(ResourceAlreadyExistsException.class, () -> tagService.add(tagDTO));
    assertEquals("Tag already exists, can't create duplicate entry for the same!",
        exception.getMessage());
  }


  // Test cases related to updating existing tag
  @Test
  void testUpdate_When_KeyIdExists_Then_Update_Tag() {

    LocalDateTime updatedTimeStamp = LocalDateTime.now();

    TagDTO tagDTO = TagDTO.builder().id(2L).name("Facebook").build();

    Tag updatedTag = Tag.builder().id(2L).name("Facebook").createdAt(timeStamp)
        .updatedAt(updatedTimeStamp).build();

    when(tagRepository.findById(2L)).thenReturn(Optional.of(new Tag()));
    when(tagRepository.save(any(Tag.class))).thenReturn(updatedTag);

    TagDTO returnedTagDTO = tagService.update(tagDTO);

    assertEquals(tagDTO.getName(), returnedTagDTO.getName());
    assertEquals(2L, returnedTagDTO.getId());
    assertEquals(timeStamp, returnedTagDTO.getCreatedAt());
    assertEquals(updatedTimeStamp, returnedTagDTO.getUpdatedAt());
  }


  @Test
  void testUpdate_When_KeyId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {

    TagDTO tagDTO = TagDTO.builder().id(22L).name("Facebook").build();

    when(tagRepository.findById(22L)).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> tagService.update(tagDTO));
    assertEquals("Tag does not exists for Id 22", exception.getMessage());
  }

  // Test cases related to deleting Tag By Id
  @Test
  void testDeleteById_When_KeyIdExists_Then_Delete_Tag() {
    Long id = 50L;

    when(tagRepository.existsById(id)).thenReturn(true);
    tagService.deleteById(id);
    verify(tagRepository).deleteById(id);
  }

  @Test
  void testDeleteById_When_KeyId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {
    Long id = 50L;
    when(tagRepository.existsById(id)).thenReturn(false);

    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> tagService.deleteById(id));
    assertEquals("Tag does not exists for Id 50", exception.getMessage());
  }

  // Test cases for Tag Subscription
  @Test
  void testSubscribe_When_KeyTagIdExists_Then_Subscribe_Tag() {
    Long userId = 1L;
    Long tagId = 1L;

    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    Tag tag = new Tag(1L, "Youtube", timeStamp, timeStamp, userList, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    tagService.subscribe(1L, userId);

    verify(tagRepository, times(1)).save(any(Tag.class));
  }

  @Test
  void testSubscribe_When_KeyTagId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {
    Long userId = 1L;
    Long tagId = 50L;

    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> tagService.subscribe(tagId, userId));
    assertEquals("Tag does not exists for Id 50", exception.getMessage());
  }

  // Test cases for Tag UnSubscription
  @Test
  void testUnSubscribe_When_KeyTagIdExists_Then_UnSubscribe_Tag() {
    Long userId = 1L;
    Long tagId = 1L;

    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    Tag tag = new Tag(1L, "Youtube", timeStamp, timeStamp, userList, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    tagService.unsubscribe(1L, userId);

    verify(tagRepository, times(1)).save(any(Tag.class));
  }

  @Test
  void testUnSubscribe_When_KeyTagId_Does_Not_Exists_Then_Throw_ResourceNotFoundException() {
    Long userId = 1L;
    Long tagId = 50L;

    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, null, null, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(ResourceNotFoundException.class, () -> tagService.unsubscribe(tagId, userId));
    assertEquals("Tag does not exists for Id 50", exception.getMessage());
  }

  // Test cases related to getAllSubscribed method
  @Test
  void testGetAllSubscribed_Return_All_Tags_Subscribed_By_User() {
    Long userId = 1L;

    User user = new User(userId, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123",
        "dhaskhdilldalskk", timeStamp, timeStamp, UserRole.READER, tagList, null, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    List<TagDTO> expectedTagDTOList = tagDTOList;
    List<TagDTO> actualTagDTOList = tagService.getAllSubscribed(userId);
    assertEquals(expectedTagDTOList, actualTagDTOList);
  }

}
