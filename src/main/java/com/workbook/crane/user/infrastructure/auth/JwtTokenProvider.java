package com.workbook.crane.user.infrastructure.auth;

import com.workbook.crane.user.domain.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret.key}")
  private String secretKey;

  @Value("${jwt.token.valid.time}")
  private long tokenValidTime;

  private final UserDetailsServiceImpl userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String generateToken(UserDetailsImpl userDetails) {
    Map<String, Object> claims = new HashMap<>();
    boolean isUser =
        userDetails.getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_" + Role.USER));
    if (isUser) {
      claims.put("role", "user");
      claims.put("myName", userDetails.getUsername());
    }
    return doGenerateToken(claims, userDetails.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + (tokenValidTime * 1000)))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  // JWT ???????????? ?????? ?????? ??????
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // ???????????? ?????? ?????? ??????
  public String getUserPk(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  // Request??? Header?????? token ?????? ?????????.
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION);
  }

  // ????????? ????????? + ???????????? ??????
  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      log.warn("????????? ??????", e);
      return false;
    }
  }
}
