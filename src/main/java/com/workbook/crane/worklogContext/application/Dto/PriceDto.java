package com.workbook.crane.worklogContext.application.Dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklogContext.domain.model.MoneyUnit;
import com.workbook.crane.worklogContext.domain.model.Price;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PriceDto extends BaseDto<Price> {
  private final long pricePerUnit;
  private final MoneyUnit unit;

  @Override
  public Price toEntity() {
    return Price.of(pricePerUnit, unit);
  }
}
