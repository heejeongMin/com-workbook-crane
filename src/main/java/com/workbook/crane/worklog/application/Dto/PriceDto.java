package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.worklog.domain.model.MoneyUnit;
import com.workbook.crane.worklog.domain.model.Price;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PriceDto extends BaseDto<Price> {
  private final double pricePerUnit;
  private final MoneyUnit unit;

  @Override
  public Price toEntity() {
    return Price.of(pricePerUnit, unit);
  }
}
