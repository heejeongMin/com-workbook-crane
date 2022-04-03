package com.workbook.crane.worklog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EquipmentType {
  CRANE("크레인");

  private String name;
}
