package com.workbook.crane.user.domain.model;

import com.workbook.crane.user.application.model.command.SignupCommand;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workbook.crane.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

  @Column(name = "full_name")
  private String fullname;

	@Column(name = "email")
	private String email;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "role", columnDefinition = "TINYINT")
  private Role role;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

	public static User create(SignupCommand command, PasswordEncoder passwordEncoder){
		User user = new User();
		user.username = command.getUsername();
		user.password = passwordEncoder.encode(command.getPassword());
		user.fullname = command.getFullname();
		user.email = command.getEmail();
    user.role = command.getRole();
		return user;
	}

  public void changeEmail(String email){
    this.email = email;
  }

  public void changePassword(String password){
    this.password = password;
  }
}
