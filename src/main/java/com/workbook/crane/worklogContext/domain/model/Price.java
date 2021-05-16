package com.workbook.crane.worklogContext.domain.model;

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
public class Price {

  @Column(name = "price_per_unit")
  private long pricePerUnit;

  @Column(name = "price_unit")
  private MoneyUnit unit;

  public static Price of(long pricePerUnit, MoneyUnit unit){
    return new Price(pricePerUnit, unit);
  }
}
