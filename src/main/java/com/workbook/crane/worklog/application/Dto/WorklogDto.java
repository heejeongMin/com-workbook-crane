package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.worklog.domain.model.Money;
import com.workbook.crane.worklog.domain.model.WorkPeriod;
import com.workbook.crane.worklog.domain.model.Worklog;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorklogDto extends BaseDto<Worklog> {

  private Long id;
  private HeavyEquipmentDto heavyEquipmentDto;
  private WorkLocationDto workLocationDto;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private boolean isPerformed;
  private boolean isPaymentCollected;
  private Money total;
  private LocalDateTime deletedAt;

  @Builder
  public WorklogDto(Long id, HeavyEquipmentDto heavyEquipmentDto, WorkLocationDto workLocationDto,
      LocalDateTime startDate, LocalDateTime endDate, boolean isPerformed,
      boolean isPaymentCollected, LocalDateTime deletedAt) {
    this.id = id;
    this.heavyEquipmentDto = heavyEquipmentDto;
    this.workLocationDto = workLocationDto;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isPerformed = isPerformed;
    this.isPaymentCollected = isPaymentCollected;
    this.deletedAt = deletedAt;
  }

  @Override
  public Worklog toEntity() {
    return Worklog.builder()
        .heavyEquipment(heavyEquipmentDto.toEntity())
        .workLocation(workLocationDto.toEntity())
        .workPeriod(new WorkPeriod(startDate, endDate))
        .isPerformed(isPerformed)
        .isPaymentCollected(isPaymentCollected)
        .deletedAt(deletedAt)
        .build();
  }

  public WorklogDto setCalculatedTotal(Money total) {
    this.total = total;
    return this;
  }


}
