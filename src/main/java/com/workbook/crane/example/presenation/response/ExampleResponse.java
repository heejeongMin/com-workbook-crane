package com.workbook.crane.example.presenation.response;

import com.workbook.crane.example.presenation.dto.ExampleDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExampleResponse {
  private final ExampleDto dto;
}