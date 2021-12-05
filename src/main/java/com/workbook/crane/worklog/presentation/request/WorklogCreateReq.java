package com.workbook.crane.worklog.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import com.workbook.crane.common.BaseRequest;
import com.workbook.crane.partner.application.dto.PartnerDto;
import com.workbook.crane.partner.presentation.request.PartnerReq;
import com.workbook.crane.worklog.application.Dto.WorkLocationDto;
import com.workbook.crane.worklog.application.Dto.WorklogDto;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorklogCreateReq extends BaseRequest<WorklogDto> {

  @JsonFormat(shape= Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  @NotNull
  private LocalDateTime startDate;
  @JsonFormat(shape= Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  @NotNull
  private LocalDateTime endDate;
  @NotNull
  private Long equipmentId;
  @NotNull
  private String city;
  @NotNull
  private String gu;
  @NotNull
  private String dong;
  @NotNull
  private Long partnerId;

  @Override
  public WorklogDto toDto(){
    return new WorklogDto().builder()
        .equipmentId(equipmentId)
        .workLocationDto(new WorkLocationDto(city, gu, dong))
        .startDate(startDate)
        .endDate(endDate)
        .partnerDto(PartnerDto.of(partnerId, new PartnerReq()))
        .build();
  }

}
