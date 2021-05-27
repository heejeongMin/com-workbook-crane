package com.workbook.crane.worklogContext.presenation.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.workbook.crane.worklogContext.domain.model.EquipmentType;
import com.workbook.crane.worklogContext.domain.model.EquipmentUnit;
import com.workbook.crane.worklogContext.domain.model.HeavyEquipment;
import com.workbook.crane.worklogContext.domain.model.MoneyUnit;
import com.workbook.crane.worklogContext.domain.model.Price;
import com.workbook.crane.worklogContext.domain.model.WorkLocation;
import com.workbook.crane.worklogContext.domain.model.WorkPeriod;
import com.workbook.crane.worklogContext.domain.model.WorkTimeUnit;
import com.workbook.crane.worklogContext.domain.model.Worklog;
import com.workbook.crane.worklogContext.domain.repository.HeavyEquipmentRepository;
import com.workbook.crane.worklogContext.domain.repository.WorklogRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class WorklogControllerTest {

  @Autowired
  HeavyEquipmentRepository heavyEquipmentRepository;

  @Autowired
  WorklogRepository worklogRepository;

  @BeforeAll
  void createBaseRecord() {
    heavyEquipmentRepository.save(
        HeavyEquipment.builder()
            .equipmentType(EquipmentType.CRANE)
            .equipmentUnit(EquipmentUnit.TON)
            .equipmentWeight(5)
            .price(Price.of(100000L, MoneyUnit.WON))
            .build());
    heavyEquipmentRepository.save(
        HeavyEquipment.builder()
            .equipmentType(EquipmentType.CRANE)
            .equipmentUnit(EquipmentUnit.TON)
            .equipmentWeight(10)
            .price(Price.of(300000L, MoneyUnit.WON))
            .build());
    heavyEquipmentRepository.save(
        HeavyEquipment.builder()
            .equipmentType(EquipmentType.CRANE)
            .equipmentUnit(EquipmentUnit.TON)
            .equipmentWeight(15)
            .price(Price.of(450000L, MoneyUnit.WON))
            .build());
  }


  @Test
  void should_Create_Worklog_Record_When_User_Save() {
    List<HeavyEquipment> heavyEquipmentList = heavyEquipmentRepository.findAll();


    Worklog worklog = worklogRepository.save(
        Worklog.builder()
            .heavyEquipment(heavyEquipmentList.get(0))
            .workLocation(WorkLocation.of("서울시", "도봉구", "창4동"))
            .workPeriod(new WorkPeriod(LocalDateTime.now(),
                LocalDateTime.now().plus(4, ChronoUnit.HOURS)))
            .build());


    assertEquals(heavyEquipmentList.get(0).getId(), worklog.getHeavyEquipment().getId());
  }

  @Test
  void should_Read_Worklog_Record_When_User_Search() {
    System.out.println(123);
    //기존 근무일정을 찾기
    //페이징 기능
  }

  @Test
  void should_Update_Worklog_Record_When_User_Modify() {
    System.out.println(123);

    //근무일정을 찾고
    //수정하고
    //조회

  }

  @Test
  void should_Delete_Worklog_Record_When_User_Delete() {
    System.out.println(123);
    //근무일정을 찾고
    //삭제 (소프트 딜리트)
  }

}