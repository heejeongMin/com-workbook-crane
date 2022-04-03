package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.partner.application.model.info.PartnerSearchInfo;
import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.worklog.application.model.info.WorklogInfo;
import com.workbook.crane.worklog.application.model.info.WorklogInfo.PartnerInfo;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorklogDto {

  private Long id;
  private HeavyEquipmentDto heavyEquipmentDto;
  private String location;
  private WorkPeriodDto workPeriodDto;
  private PartnerDto partnerDto;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  public static WorklogDto from(WorklogInfo info) {
    WorklogDto dto = new WorklogDto();
    dto.id = info.getId();
    dto.heavyEquipmentDto = HeavyEquipmentDto.from(info.getHeavyEquipmentInfo());
    dto.location = info.getLocation();
    dto.workPeriodDto = WorkPeriodDto.from(info.getWorkPeriodInfo());
    dto.partnerDto = PartnerDto.from(info.getPartnerInfo());
    dto.createdAt = info.getCreatedAt();
    dto.deletedAt = info.getDeletedAt();
    return dto;
  }

  @Getter
  private static class PartnerDto {
    private Long id;
    private String partnerNumber;
    private String companyName;

    public static PartnerDto from(PartnerInfo info){
      PartnerDto dto = new PartnerDto();
      dto.id = info.getId();
      dto.partnerNumber = info.getPartnerNumber();
      dto.companyName = info.getCompanyName();
      return dto;
    }
  }
}
