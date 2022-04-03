package com.workbook.crane.user.presenation.model.response;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class SigninResponse {

  private boolean result;
  private String token;

  public static SigninResponse from(String token) {
    SigninResponse response = new SigninResponse();
    if (StringUtils.isEmpty(token)) {
      return response;
    }
    response.result = true;
    response.token = token;
    return response;
  }
}
