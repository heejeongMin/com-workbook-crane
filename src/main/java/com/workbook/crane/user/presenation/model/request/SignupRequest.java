package com.workbook.crane.user.presenation.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequest {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String fullname;
  @NotBlank
  private String email;
}
