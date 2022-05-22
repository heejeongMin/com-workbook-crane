package com.workbook.crane.user.presenation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.workbook.crane.user.application.model.command.SigninCommand;
import com.workbook.crane.user.application.model.command.SignupCommand;
import com.workbook.crane.user.application.service.AuthService;
import com.workbook.crane.user.application.service.UserWorkInfoService;
import com.workbook.crane.user.presenation.model.request.ChangeEmailRequest;
import com.workbook.crane.user.presenation.model.request.ChangePasswordRequest;
import com.workbook.crane.user.presenation.model.request.SigninRequest;
import com.workbook.crane.user.presenation.model.request.SignupRequest;
import com.workbook.crane.user.presenation.model.response.SigninResponse;
import com.workbook.crane.user.presenation.model.response.UserDetailResponse;
import com.workbook.crane.user.presenation.model.response.UserWorkInfoResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

  private final AuthService authService;
  private final UserWorkInfoService userWorkInfoService;

  @PostMapping(value = "/signup", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> signup(@RequestBody SignupRequest signupRequest) throws Exception {
    return ResponseEntity.created(
            linkTo(AuthController.class).toUri())
        .body(authService.signup(SignupCommand.from(signupRequest)));
  }

  @PostMapping(value = "/signin", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest)
      throws Exception {
    return ResponseEntity.ok(SigninResponse.from(
        authService.signin(SigninCommand.from(signinRequest))));
  }

  @GetMapping(value = "/user/detail", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDetailResponse> getUserDetail(Principal principal) throws Exception {
    return ResponseEntity.ok(
        UserDetailResponse.from(authService.getUserDetail(principal.getName())));
  }

  @PutMapping(value = "/email", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDetailResponse> changeEmail(
      Principal principal,
      @RequestBody ChangeEmailRequest req) throws Exception {
    return ResponseEntity.ok(
        UserDetailResponse.from(authService.changeEmail(principal.getName(), req.getEmail())));
  }

  @PutMapping(value = "/password", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDetailResponse> changePassword(
      Principal principal,
      @RequestBody ChangePasswordRequest req) throws Exception {
    return ResponseEntity.ok(
        UserDetailResponse.from(
            authService.changePassword(principal.getName(), req.getPassword())));
  }

  @GetMapping(value = "/workInfo", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<UserWorkInfoResponse> getAssessment(Principal principal) throws Exception {
    return ResponseEntity.ok(
        UserWorkInfoResponse.of(userWorkInfoService.getUserWorkInfo(principal.getName())));
  }
}
