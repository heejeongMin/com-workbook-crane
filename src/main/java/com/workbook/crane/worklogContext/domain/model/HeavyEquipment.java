package com.workbook.crane.worklogContext.domain.model;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklogContext.application.Dto.HeavyEquipmentDto;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class HeavyEquipment extends BaseEntity<HeavyEquipmentDto> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "equipment_Type")
  private EquipmentType equipmentType;

  @Enumerated(EnumType.STRING)
  @Column(name = "equipment_unit")
  private EquipmentUnit equipmentUnit;

  @Column(name = "equipment_weight")
  private long equipmentWeight;

  @Embedded
  private Price price;

  @Override
  public HeavyEquipmentDto toDto() {
    return null;
  }

  @Builder
  public HeavyEquipment(Long id, EquipmentType equipmentType, EquipmentUnit equipmentUnit,
      long equipmentWeight, Price price) {
    this.id = id;
    this.equipmentType = equipmentType;
    this.equipmentUnit = equipmentUnit;
    this.equipmentWeight = equipmentWeight;
    this.price = price;
  }

  public Money calculateTotal(long hours){
    return new Money(hours * price.getPricePerUnit(), price.getUnit());
  }
}
