package com.workbook.crane.worklog.presenation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import com.workbook.crane.common.BaseRequest;
import com.workbook.crane.worklog.application.Dto.WorkLocationDto;
import com.workbook.crane.worklog.application.Dto.HeavyEquipmentDto;
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
  private HeavyEquipmentDto heavyEquipmentDto;
  @NotNull
  private String city;
  @NotNull
  private String gu;
  @NotNull
  private String dong;
//  private Partner partner;

  @Override
  public WorklogDto toDto(){
    return new WorklogDto().builder()
        .heavyEquipmentDto(heavyEquipmentDto)
        .workLocationDto(new WorkLocationDto(city, gu, dong))
        .startDate(startDate)
        .endDate(endDate)
        .build();
  }

}
