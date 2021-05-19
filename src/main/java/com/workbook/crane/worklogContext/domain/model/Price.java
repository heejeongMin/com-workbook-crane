package com.workbook.crane.worklogContext.domain.model;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklogContext.application.Dto.PriceDto;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Price extends BaseEntity<PriceDto> {

  @Column(name = "price_per_unit")
  private long pricePerUnit;

  @Column(name = "price_unit")
  private MoneyUnit unit;

  public static Price of(long pricePerUnit, MoneyUnit unit){
    return new Price(pricePerUnit, unit);
  }

  @Override
  public PriceDto toDto() {
    return new PriceDto(pricePerUnit, unit);
  }
}
