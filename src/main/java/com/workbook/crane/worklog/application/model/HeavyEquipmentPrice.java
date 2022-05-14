package com.workbook.crane.worklog.application.model;

import com.workbook.crane.worklog.domain.model.EquipmentPrice;
import com.workbook.crane.worklog.presentation.request.HeavyEquipmentCreateRequest;
import lombok.Getter;

@Getter
public class HeavyEquipmentPrice {
  private final double halfDayAmount;
  private final double fullDayAmount;
  private final double nightShiftAmount;

  public HeavyEquipmentPrice (HeavyEquipmentCreateRequest.HeavyEquipmentPrice price){
    this.halfDayAmount = price.getHalfDayAmount();
    this.fullDayAmount = price.getFullDayAmount();
    this.nightShiftAmount = price.getNightShiftAmount();
  }

  public HeavyEquipmentPrice (EquipmentPrice price){
    this.halfDayAmount = price.getHalfDayAmount();
    this.fullDayAmount = price.getFullDayAmount();
    this.nightShiftAmount = price.getNightShiftAmount();
  }
}
