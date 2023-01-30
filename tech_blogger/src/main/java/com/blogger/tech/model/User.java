package com.blogger.tech.model;

import java.time.LocalDateTime;
import java.util.Objects;
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


	@Override
	public int hashCode() {
		return Objects.hash(articleList, collectionList, createdAt, email, firstName, id, lastName, password, tagList,
				updatedAt, userRole, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(articleList, other.articleList) && Objects.equals(collectionList, other.collectionList)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(tagList, other.tagList) && Objects.equals(updatedAt, other.updatedAt)
				&& userRole == other.userRole && Objects.equals(username, other.username);
	}
	
}
