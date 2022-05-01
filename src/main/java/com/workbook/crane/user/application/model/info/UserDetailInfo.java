package com.workbook.crane.user.application.model.info;

import com.workbook.crane.user.domain.model.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserDetailInfo {

  private Long id;
  private String username;
  private String password;
  private String fullname;
  private String email;
  private LocalDateTime createdAt;

  public static UserDetailInfo from(User user) {
    UserDetailInfo info = new UserDetailInfo();
    info.id = user.getId();
    info.username = user.getUsername();
    info.password = user.getPassword();
    info.fullname = user.getFullname();
    info.email = user.getEmail();
    info.createdAt = user.getCreatedAt();
    return info;
  }
}