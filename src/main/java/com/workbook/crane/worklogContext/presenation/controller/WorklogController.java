package com.workbook.crane.worklogContext.presenation.controller;

import com.workbook.crane.worklogContext.application.Service.WorklogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WorklogController {

  private final WorklogService worklogService;


}
