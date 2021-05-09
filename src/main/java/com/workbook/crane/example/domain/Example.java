package com.workbook.crane.example.domain;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.example.presenation.dto.ExampleDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "example_table")
public class Example extends BaseEntity<ExampleDto> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "example_name")
  private String name;

  @Builder
  public Example(String name){
    this.name = name;
  }

  @Override
  public ExampleDto toDto(){
    return new ExampleDto().builder()
                            .name(this.name)
                            .build();
  }
}