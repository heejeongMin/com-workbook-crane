package com.workbook.crane.example.presenation;

import com.workbook.crane.example.application.ExampleService;
import com.workbook.crane.example.presenation.dto.ExampleDto;
import com.workbook.crane.example.presenation.request.ExampleRequest;
import com.workbook.crane.example.presenation.response.ExampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleController {

  public final ExampleService exampleService;

  @GetMapping(value = "/")
  public ResponseEntity<ExampleResponse> ExampleController(@ModelAttribute ExampleRequest req) {
    return ResponseEntity.ok(new ExampleResponse(exampleService.exampleService(
        new ExampleDto().builder()
            .name(req.getName())
            .build())));
  }
}
