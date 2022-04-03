package com.workbook.crane.worklog.domain.model;

import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.application.model.command.WorklogCreateCommand;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Table(name = "worklog")
@Entity
public class Worklog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "equipment_id")
  private HeavyEquipment equipment;

  @Column(name = "location")
  private String location;

  @Embedded
  private WorkPeriod workPeriod;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "partner_id")
  private Partner partner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public static Worklog create(
      WorklogCreateCommand command, User user, Partner partner, HeavyEquipment heavyEquipment){
    Worklog worklog = new Worklog();
    worklog.equipment = heavyEquipment;
    worklog.location = command.getLocation();
    worklog.workPeriod = new WorkPeriod(command.getStartedAt(), command.getFinishedAt());
    worklog.partner = partner;
    worklog.user = user;
    worklog.createdAt = LocalDateTime.now();
    return worklog;
  }

  public void markWorklogAsDeleted(){
    this.deletedAt = LocalDateTime.now();
  }
}
