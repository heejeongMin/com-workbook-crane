package com.workbook.crane.partner.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "partner_number_tracker")
@Entity
public class PartnerNumberTracker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "SMALLINT")
  private Long id;

  @Column(name = "identifier", columnDefinition = "CHAR")
  private String identifier;

  @Column(name = "identifier_seq", columnDefinition = "SMALLINT")
  private long seq;

  public void increaseIdentifierSeq(){
    ++this.seq;
  }
}
