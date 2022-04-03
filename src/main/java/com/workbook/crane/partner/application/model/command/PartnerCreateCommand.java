package com.workbook.crane.partner.application.model.command;

import com.workbook.crane.partner.presentation.request.PartnerCreateRequest;
import lombok.Getter;

@Getter
public class PartnerCreateCommand {
  private String companyName;
  private String ceoName;
  private String phoneNumber;

  public static PartnerCreateCommand from(PartnerCreateRequest req) {
    PartnerCreateCommand command = new PartnerCreateCommand();
    command.companyName = req.getCompanyName();
    command.ceoName = req.getCeoName();
    command.phoneNumber = req.getPhoneNumber();
    return command;
  }
}
