package com.workbook.crane.user.infrastructure.auth;

import com.workbook.crane.user.domain.model.Role;
import com.workbook.crane.user.domain.model.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private static final String ROLE_PREFIX = "ROLE_";
  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Role role = user.getRole();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + role.toString());
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(authority);

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
