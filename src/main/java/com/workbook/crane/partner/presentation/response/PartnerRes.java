package com.workbook.crane.partner.presentation.response;

import java.util.List;
import com.workbook.crane.partner.application.dto.PartnerDto;

import java.util.Map;
import lombok.Getter;

@Getter
public class PartnerRes {

  private List<PartnerDto> partnerDtoList;
  private long totalItems;

  public static PartnerRes from(Map<String, Object> map) {
    PartnerRes res = new PartnerRes();
    res.partnerDtoList = (List<PartnerDto>) map.get("partnerDtoList");
    res.totalItems = (Long) map.get("totalItems");
    return res;
  }

  public static PartnerRes from(List<PartnerDto> partnerDtos) {
    PartnerRes res = new PartnerRes();
    res.partnerDtoList = partnerDtos;
    res.totalItems = partnerDtos.size();
    return res;
  }
}
