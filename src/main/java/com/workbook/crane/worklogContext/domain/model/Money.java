package com.workbook.crane.worklogContext.domain.model;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Embeddable
public class Money {

  private long amount;
  private MoneyUnit unit;

}
