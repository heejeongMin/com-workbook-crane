package com.workbook.crane.worklogContext.application.Dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.worklogContext.domain.model.EquipmentType;
import com.workbook.crane.worklogContext.domain.model.EquipmentUnit;
import com.workbook.crane.worklogContext.domain.model.HeavyEquipment;
import com.workbook.crane.worklogContext.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeavyEquipmentDto extends BaseDto<HeavyEquipment> {
  private EquipmentType equipmentType;
  private long weight;
  private EquipmentUnit equipmentUnit;
  private PriceDto priceDto;

  @Override
  public HeavyEquipment toEntity() {
    return HeavyEquipment.builder()
        .equipmentType(equipmentType)
        .equipmentUnit(equipmentUnit)
        .equipmentWeight(weight)
        .price(priceDto.toEntity())
        .build();
  }
}
