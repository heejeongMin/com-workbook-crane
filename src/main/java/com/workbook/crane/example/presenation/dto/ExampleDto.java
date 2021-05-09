package com.workbook.crane.example.presenation.dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.example.domain.Example;
import javax.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExampleDto extends BaseDto<Example> {

  @Column(name = "example_name")
  private String name;


  @Builder
  public ExampleDto (String name){
    this.name = name;
  }

  @Override
  public Example toEntity() {
    return new Example().builder()
                        .name(this.name)
                        .build();
  }
}