package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.worklog.application.model.info.HeavyEquipmentInfo;
import com.workbook.crane.worklog.domain.model.EquipmentType;
import com.workbook.crane.worklog.domain.model.EquipmentUnit;
import java.time.Instant;
import lombok.Getter;

@Getter
public class HeavyEquipmentDto {

  private Long id;
  private EquipmentType equipmentType;
  private EquipmentUnit equipmentUnit;
  private long equipmentWeight;
  private Instant createdAt;
  private Instant deletedAt;

  public static HeavyEquipmentDto from(HeavyEquipmentInfo info) {
    HeavyEquipmentDto dto = new HeavyEquipmentDto();
    dto.id = info.getId();
    dto.equipmentType = info.getEquipmentType();
    dto.equipmentUnit = info.getEquipmentUnit();
    dto.equipmentWeight = info.getEquipmentWeight();
    dto.createdAt = info.getCreatedAt();
    dto.deletedAt = info.getDeletedAt();
    return dto;
  }
}
