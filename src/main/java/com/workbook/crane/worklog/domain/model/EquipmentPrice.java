package com.workbook.crane.worklog.domain.model;

import com.workbook.crane.worklog.application.model.HeavyEquipmentPrice;
import javax.persistence.Column;
import javax.persistence.Embeddable;;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class EquipmentPrice {

  @Column(name = "half_day_amount")
  private Double halfDayAmount;
  @Column(name = "full_day_amount")
  private Double fullDayAmount;
  @Column(name = "night_shift_amount")
  private Double nightShiftAmount;

  public EquipmentPrice(HeavyEquipmentPrice price) {
    this.halfDayAmount = price.getHalfDayAmount();
    this.fullDayAmount = price.getFullDayAmount();
    this.nightShiftAmount = price.getNightShiftAmount();
  }
}