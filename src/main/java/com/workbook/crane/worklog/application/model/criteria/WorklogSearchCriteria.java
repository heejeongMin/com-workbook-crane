package com.workbook.crane.worklog.application.model.criteria;

import java.time.LocalDateTime;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;

@Getter
public class WorklogSearchCriteria {

  private LocalDateTime workDateFrom;
  private LocalDateTime workDateTo;
  private String partnerName;
  private PageRequest pageRequest;
  private String username;

  public static WorklogSearchCriteria of(
      LocalDateTime workDateFrom,
      LocalDateTime workDateTo,
      String partnerName,
      int page,
      int size,
      String username) {
    WorklogSearchCriteria criteria = new WorklogSearchCriteria();
    criteria.workDateFrom = workDateFrom;
    criteria.workDateTo = workDateTo;
    criteria.partnerName =
        StringUtils.isEmpty(partnerName) ? "" : partnerName;
    criteria.pageRequest = PageRequest.of(page, size);
    criteria.username = username;
    return criteria;
  }
}
