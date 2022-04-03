package com.workbook.crane.user.application.model.command;

import com.workbook.crane.user.domain.model.Role;
import com.workbook.crane.user.presenation.model.request.SignupRequest;
import lombok.Getter;

@Getter
public class SignupCommand {

  private String username;
  private String password;
  private String fullname;
  private String email;
  private Role role;;

  public static SignupCommand from(SignupRequest request) {
    SignupCommand command = new SignupCommand();
    command.username = request.getUsername();
    command.password = request.getPassword();
    command.fullname = request.getFullname();
    command.email = request.getEmail();
    command.role = Role.USER;
    return command;
  }
}
