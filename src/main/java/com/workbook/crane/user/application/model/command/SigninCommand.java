package com.workbook.crane.user.application.model.command;

import com.workbook.crane.user.presenation.model.request.SigninRequest;
import com.workbook.crane.user.presenation.model.request.SignupRequest;
import lombok.Getter;

@Getter
public class SigninCommand {

  private String username;
  private String password;

  public static SigninCommand from(SigninRequest request) {
    SigninCommand command = new SigninCommand();
    command.username = request.getUsername();
    command.password = request.getPassword();
    return command;
  }
}
