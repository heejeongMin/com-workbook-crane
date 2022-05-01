package com.workbook.crane.worklog.application.model.info;

import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.worklog.domain.model.WorkTime;
import com.workbook.crane.worklog.domain.model.Worklog;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorklogInfo {
  private Long id;
  private HeavyEquipmentInfo heavyEquipmentInfo;
  private String location;

  private WorkTime workTime;
  private PartnerInfo partnerInfo;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  public static WorklogInfo from(Worklog worklog) {
    WorklogInfo info = new WorklogInfo();
    info.id = worklog.getId();
    info.heavyEquipmentInfo = HeavyEquipmentInfo.from(worklog.getEquipment());
    info.location = worklog.getLocation();
    info.workTime = worklog.getWorkTime();
    info.partnerInfo = PartnerInfo.from(worklog.getPartner());
    info.createdAt = worklog.getCreatedAt();
    info.deletedAt = worklog.getDeletedAt();
    return info;
  }

  @Getter
  public static class PartnerInfo {
    private Long id;
    private String partnerNumber;
    private String companyName;

    public static PartnerInfo from(Partner partner){
      PartnerInfo info = new PartnerInfo();
      info.id = partner.getId();
      info.partnerNumber = partner.getPartnerNumber();
      info.companyName = partner.getCompanyName();
      return info;
    }
  }
}
