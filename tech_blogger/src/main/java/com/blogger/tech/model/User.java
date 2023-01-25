package com.blogger.tech.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.blogger.tech.dto.UserDTO;
import com.blogger.tech.enums.UserRoles;

import lombok.AllArgsConstructor;
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
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BLOGUSER_SEQ")
	@SequenceGenerator(name = "BLOGUSER_SEQ",
					sequenceName = "BLOGUSER_SEQ",allocationSize = 1)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRoles userRole;
	
	@ManyToMany(mappedBy = "userList")
	private Set<Tag> tagList;
	
	@OneToMany(mappedBy = "user")
	private Set<Article> articleList;
	
	@OneToMany(mappedBy = "user")
	private Set<Collection> collectionList;
		
	
	public User(UserDTO userDTO) {
		this.id=userDTO.getId();
		this.email=userDTO.getEmail();
		this.username=userDTO.getUsername();
		this.firstName=userDTO.getFirstname();
		this.lastName=userDTO.getLastname();
		this.password=userDTO.getPassword();
		this.createdAt=userDTO.getCreatedAt()==null ? LocalDateTime.now() : userDTO.getCreatedAt();
		this.updatedAt=LocalDateTime.now();
		this.userRole=userDTO.getUserRole();
	}
	
}
