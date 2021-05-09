package com.workbook.crane.example.application;

import com.workbook.crane.example.domain.ExampleRepository;
import com.workbook.crane.example.presenation.dto.ExampleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleService {

  private final ExampleRepository exampleRepository;

  public ExampleDto exampleService (ExampleDto dto){
    return exampleRepository.save(dto.toEntity()).toDto();
  }
}