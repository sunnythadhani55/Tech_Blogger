package com.blogger.tech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.blogger.tech.dto.TagDTO;
import com.blogger.tech.dto.UserDTO;
import com.blogger.tech.enums.ArticleStatus;
import com.blogger.tech.enums.UserRoles;
import com.blogger.tech.exception.ResourceNotFoundException;
import com.blogger.tech.model.Article;
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
	
	final private LocalDateTime timeStamp=LocalDateTime.now();
	
	//creates test data before executing first test case
	@BeforeEach
	public void init() {
		
		//Creating test data as List of Tags
		tagList=new ArrayList<>();
		
		tagList.add(new Tag(1L,"Youtube",timeStamp,timeStamp, null, null));
		tagList.add(new Tag(2L,"Twitter",timeStamp, timeStamp, null, null));
		tagList.add(new Tag(3L,"Artifical Intelligence",timeStamp,timeStamp, null, null));
		tagList.add(new Tag(4L,"IOT", timeStamp, timeStamp, null, null));
		tagList.add(new Tag(5L,"Blockchain", timeStamp, timeStamp, null, null));
		
		//Creating test data as List of TagDTOs
		tagDTOList=new ArrayList<>();
		
		tagDTOList.add(new TagDTO(1L,"Youtube",timeStamp,timeStamp, null, null));
		tagDTOList.add(new TagDTO(2L,"Twitter",timeStamp, timeStamp, null, null));
		tagDTOList.add(new TagDTO(3L,"Artifical Intelligence",timeStamp,timeStamp, null, null));
		tagDTOList.add(new TagDTO(4L,"IOT", timeStamp, timeStamp, null, null));
		tagDTOList.add(new TagDTO(5L,"Blockchain", timeStamp, timeStamp, null, null));	
		
		//Creating test data as List of Users
		userList=new ArrayList<>();
		
		userList.add(new User(1L, "sunnythadhani87@gmail.com", "Sunny", "Thadhani", "Sunny123", "dhaskhdilldalskk", timeStamp, timeStamp, UserRoles.WRITER, null, null, null));
		userList.add(new User(2L, "ankitgupta@gmail.com", "Ankit", "Gupta", "Ankit123", "usdasddfsdasdda", timeStamp, timeStamp, UserRoles.ADMIN, null, null, null));
		userList.add(new User(3L, "vishalsharma@gmail.com", "Vishal", "Sharma", "Vishal123", "oioioioioiohhjkh", timeStamp, timeStamp, UserRoles.READER, null, null, null));
		
		//Creating test data as List of User DTOs
		userDTOList=new ArrayList<>();
		
		userDTOList.add(new UserDTO(userList.get(0), false, false, false));
		userDTOList.add(new UserDTO(userList.get(1), false, false, false));
		userDTOList.add(new UserDTO(userList.get(2), false, false, false));
		
		
		//Creating test data as List of Articles
		articleList=new ArrayList<>();
		
		articleList.add(new Article(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp, timeStamp, userList.get(1), null, null, null));
		articleList.add(new Article(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp, timeStamp, userList.get(1), null, null, null));
		articleList.add(new Article(3L, "Title 3", "Content 3", ArticleStatus.PUBLISHED, timeStamp, timeStamp, userList.get(1), null, null, null));
		articleList.add(new Article(4L, "Title 4", "Content 4", ArticleStatus.DRAFT, timeStamp, timeStamp, userList.get(1), null, null, null));
		
		
		//Creating test data as List of Article DTOs
		articleDTOList=new ArrayList<>();
		
		articleDTOList.add(new ArticleDTO(1L, "Title 1", "Content 1", ArticleStatus.PUBLISHED, timeStamp, timeStamp, null, null, null, null));
		articleDTOList.add(new ArticleDTO(2L, "Title 2", "Content 2", ArticleStatus.DRAFT, timeStamp, timeStamp, null, null, null, null));
		articleDTOList.add(new ArticleDTO(3L, "Title 3", "Content 3", ArticleStatus.PUBLISHED, timeStamp, timeStamp, null, null, null, null));
		articleDTOList.add(new ArticleDTO(4L, "Title 4", "Content 4", ArticleStatus.DRAFT, timeStamp, timeStamp, null, null, null, null));
		
	}
	
	//FindAll method test cases
	@Test
	void should_find_and_return_all_Articles() {		
		when(articleRepository.findAll()).thenReturn(articleList);		
		List<ArticleDTO> actualArticleDTOList = articleService.getAll();		
		assertEquals(4, actualArticleDTOList.size());
		assertEquals(articleDTOList, actualArticleDTOList);
	}
	
	
}
