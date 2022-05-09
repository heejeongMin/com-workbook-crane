package com.workbook.crane.user.presenation.model.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {

  @NotNull
  private String password;
}
