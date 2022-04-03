package com.workbook.crane.user.application.service;

import com.workbook.crane.user.application.model.info.UserDetailInfo;
import com.workbook.crane.user.infrastructure.auth.JwtTokenProvider;
import com.workbook.crane.user.infrastructure.auth.UserDetailsImpl;
import com.workbook.crane.user.application.model.command.SigninCommand;
import com.workbook.crane.user.application.model.command.SignupCommand;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.user.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public boolean signup(SignupCommand command) throws Exception {
    this.validateIfUserAlreadyExist(command);
    User user = User.create(command, passwordEncoder);
    userRepository.save(user);
    return true;
  }

  @Transactional
  public String signin(SigninCommand command) throws Exception {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
    return jwtTokenProvider.generateToken(principal);
  }

  @Transactional
  public UserDetailInfo getUserDetail(String username) throws Exception {
    return UserDetailInfo.from(this.getUserOrElseThrow(username));
  }

  public User getUserOrElseThrow(String username) throws Exception {
    Optional<User> user = userRepository.findByUsername(username);
    return user.orElseThrow(() -> new Exception("동일한 이메일로 이미 회원이 존재합니다."));
  }

  private void validateIfUserAlreadyExist(SignupCommand command) throws Exception {
    User user = userRepository.findByEmailAndDeletedAtIsNull(command.getEmail());
    if (user != null) {
      throw new Exception("동일한 이메일로 이미 회원이 존재합니다.");
    }
  }
}
