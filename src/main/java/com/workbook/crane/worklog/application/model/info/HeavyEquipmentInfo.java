package com.workbook.crane.worklog.application.model.info;

import static java.util.stream.Collectors.toList;

import com.workbook.crane.worklog.domain.model.EquipmentType;
import com.workbook.crane.worklog.domain.model.EquipmentUnit;
import com.workbook.crane.worklog.domain.model.HeavyEquipment;
import java.time.Instant;
import java.util.List;
import lombok.Getter;

@Getter
public class HeavyEquipmentInfo {

  private Long id;
  private EquipmentType equipmentType;
  private EquipmentUnit equipmentUnit;
  private long equipmentWeight;
  private Double equipmentPricePerDay;
  private Instant createdAt;
  private Instant deletedAt;

  public static HeavyEquipmentInfo from(HeavyEquipment heavyEquipment) {
    HeavyEquipmentInfo info = new HeavyEquipmentInfo();
    info.id = heavyEquipment.getId();
    info.equipmentType = heavyEquipment.getEquipmentType();
    info.equipmentUnit = heavyEquipment.getEquipmentUnit();
    info.equipmentWeight = heavyEquipment.getEquipmentWeight();
    info.equipmentPricePerDay = heavyEquipment.getEquipmentPricePerDay();
    info.createdAt = heavyEquipment.getCreatedAt();
    info.deletedAt = heavyEquipment.getDeletedAt();
    return info;
  }

  public static List<HeavyEquipmentInfo> from(List<HeavyEquipment> heavyEquipments) {
    return heavyEquipments.stream()
        .map(HeavyEquipmentInfo::from)
        .collect(toList());
  }
}
