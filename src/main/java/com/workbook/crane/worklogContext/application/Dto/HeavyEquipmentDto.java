package com.workbook.crane.worklogContext.application.Dto;

import com.workbook.crane.worklogContext.domain.model.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeavyEquipmentDto {
  private EquipmentType equipmentType;
  private long weight;
  private String unit;
}
