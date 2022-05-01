package com.workbook.crane.user.presenation.model.response;

import com.workbook.crane.user.application.model.info.UserDetailInfo;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserDetailResponse {

  private Long id;
  private String username;
  private String password;
  private String fullname;
  private String email;
  private LocalDateTime createdAt;

  public static UserDetailResponse from(UserDetailInfo info) {
    UserDetailResponse response = new UserDetailResponse();
    response.id = info.getId();
    response.username = info.getUsername();
    response.password = info.getPassword();
    response.fullname = info.getFullname();
    response.email = info.getEmail();
    response.createdAt = info.getCreatedAt();
    return response;
  }
}
