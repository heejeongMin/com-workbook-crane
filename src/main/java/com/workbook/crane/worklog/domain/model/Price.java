package com.workbook.crane.worklog.domain.model;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklog.application.Dto.PriceDto;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
  private double pricePerUnit;

  @Enumerated(EnumType.STRING)
  @Column(name = "price_unit")
  private MoneyUnit unit;

  public static Price of(double pricePerUnit, MoneyUnit unit){
    return new Price(pricePerUnit, unit);
  }

  @Override
  public PriceDto toDto() {
    return new PriceDto(pricePerUnit, unit);
  }
}
