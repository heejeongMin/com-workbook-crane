package com.workbook.crane.user.presenation.model.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeEmailRequest {

  @NotNull
  private String email;
}
