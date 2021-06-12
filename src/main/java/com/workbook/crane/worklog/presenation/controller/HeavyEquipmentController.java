package com.workbook.crane.worklog.presenation.controller;

import com.workbook.crane.worklog.application.Service.HeavyEquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HeavyEquipmentController {

  private final HeavyEquipmentService heavyEquipmentService;

}
