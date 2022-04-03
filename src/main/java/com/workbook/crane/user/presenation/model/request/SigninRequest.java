package com.workbook.crane.user.presenation.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SigninRequest {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
}
