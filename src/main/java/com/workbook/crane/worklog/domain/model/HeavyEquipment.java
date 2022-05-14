package com.workbook.crane.worklog.domain.model;

import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.application.model.command.HeavyEquipmentCreateCommand;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "heavy_equipment")
@Entity
public class HeavyEquipment {

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
  private EquipmentPrice equipmentPrice;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "deleted_at")
  private Instant deletedAt;

  public String getEquipment() {
    return equipmentWeight + "" + this.equipmentUnit;
  }

  public static HeavyEquipment create(HeavyEquipmentCreateCommand command, User user) {
    HeavyEquipment heavyEquipment = new HeavyEquipment();
    heavyEquipment.equipmentType = command.getEquipmentType();
    heavyEquipment.equipmentUnit = command.getEquipmentUnit();
    heavyEquipment.equipmentWeight = command.getEquipmentWeight();
    heavyEquipment.equipmentPrice = new EquipmentPrice(command.getEquipmentPrice());
    heavyEquipment.user = user;
    heavyEquipment.createdAt = Instant.now();
    return heavyEquipment;
  }

  public void markAsDeleted() {
    this.deletedAt = Instant.now();
  }
}
