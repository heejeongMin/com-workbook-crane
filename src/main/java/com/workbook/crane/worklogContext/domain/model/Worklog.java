package com.workbook.crane.worklogContext.domain.model;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklogContext.application.Dto.WorklogDto;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "worklog")
@Entity
public class Worklog extends BaseEntity<WorklogDto> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(targetEntity = HeavyEquipment.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "equipment_id")
  private HeavyEquipment heavyEquipment;

//  @ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
//  @JoinColumn(name = "partner_id")
//  private Parnter partner;

  @Embedded
  private WorkLocation workLocation;

  @Embedded
  private WorkPeriod workPeriod;

  @Column(name = "is_performed")
  private boolean isPerformed;

  @Column(name = "is_payment_collected")
  private boolean isPaymentCollected;

  @Transient
  private Money amount;

  @Override
  public WorklogDto toDto() {
    return null;
  }

  @Builder
  public Worklog (Long id, HeavyEquipment heavyEquipment,
      WorkLocation workLocation, WorkPeriod workPeriod) {
    this.id = id;
    this.heavyEquipment = heavyEquipment;
    this.workLocation = workLocation;
    this.workPeriod = workPeriod;
    this.amount = heavyEquipment.calculateTotal(workPeriod.getWorkhours());
  }
}
