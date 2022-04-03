package com.workbook.crane.worklog.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorklogCreateRequest {

  @JsonFormat(shape= Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  @NotNull
  private LocalDateTime startedAt;
  @JsonFormat(shape= Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  @NotNull
  private LocalDateTime finishedAt;
  @NotNull
  private Long equipmentId;
  @NotBlank
  private String location;
  @NotNull
  private Long partnerId;
}
