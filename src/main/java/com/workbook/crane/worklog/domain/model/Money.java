package com.workbook.crane.worklog.domain.model;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Money {

  private double amount;
  private MoneyUnit unit;

}
