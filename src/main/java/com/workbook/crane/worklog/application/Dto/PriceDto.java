package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.worklog.domain.model.MoneyUnit;
import com.workbook.crane.worklog.domain.model.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class PriceDto extends BaseDto<Price> {
  private double pricePerUnit;
  private MoneyUnit unit;

  public PriceDto(){

  }

  public PriceDto(double pricePerUnit, MoneyUnit unit) {
    this.pricePerUnit = pricePerUnit;
    this.unit = unit;
  }

  @Override
  public Price toEntity() {
    return Price.of(pricePerUnit, unit);
  }
}
