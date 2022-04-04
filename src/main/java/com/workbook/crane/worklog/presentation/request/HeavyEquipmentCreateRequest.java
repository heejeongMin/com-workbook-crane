package com.workbook.crane.worklog.presentation.request;

import com.workbook.crane.worklog.domain.model.EquipmentType;
import com.workbook.crane.worklog.domain.model.EquipmentUnit;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HeavyEquipmentCreateRequest {
  @NotNull
  private EquipmentType equipmentType;
  @NotNull
  private EquipmentUnit equipmentUnit;
  private long equipmentWeight;
}
