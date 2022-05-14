package com.workbook.crane.worklog.presentation.request;

import com.workbook.crane.worklog.domain.model.EquipmentType;
import com.workbook.crane.worklog.domain.model.EquipmentUnit;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HeavyEquipmentCreateRequest {
  @NotNull
  private EquipmentType equipmentType;
  @NotNull
  private EquipmentUnit equipmentUnit;
  @NotNull
  private Long equipmentWeight;
  @Valid
  @NotNull
  private HeavyEquipmentPrice equipmentPrice;

  @Getter
  public static class HeavyEquipmentPrice {
    @NotNull
    private Double halfDayAmount;
    @NotNull
    private Double fullDayAmount;
    @NotNull
    private Double nightShiftAmount;
  }
}
