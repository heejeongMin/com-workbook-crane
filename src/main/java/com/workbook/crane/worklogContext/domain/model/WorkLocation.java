package com.workbook.crane.worklogContext.domain.model;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklogContext.application.Dto.WorkLocationDto;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WorkLocation extends BaseEntity<WorkLocationDto> {

  @Column(name = "city")
  private String city;

  @Column(name = "gu")
  private String gu;

  @Column(name = "dong")
  private String dong;


  public static WorkLocation of(String city, String gu, String dong){
    return new WorkLocation(city, gu, dong);
  }

  @Override
  public WorkLocationDto toDto() {
    return null;
  }
}
