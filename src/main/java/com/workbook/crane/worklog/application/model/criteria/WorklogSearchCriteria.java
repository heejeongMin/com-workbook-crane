package com.workbook.crane.worklog.application.model.criteria;

import com.workbook.crane.worklog.presentation.request.WorklogSearchCriteriaRequest;
import java.time.LocalDateTime;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;

@Getter
public class WorklogSearchCriteria {

  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
  private String partnerName;
  private PageRequest pageRequest;
  private String username;

  public static WorklogSearchCriteria of(
      LocalDateTime startedAt,
      LocalDateTime finishedAt,
      String partnerName,
      int page,
      int size,
      String username) {
    WorklogSearchCriteria criteria = new WorklogSearchCriteria();
    criteria.startedAt = startedAt;
    criteria.finishedAt = finishedAt;
    criteria.partnerName =
        StringUtils.isEmpty(partnerName) ? "" : partnerName;
    criteria.pageRequest = PageRequest.of(page, size);
    criteria.username = username;
    return criteria;
  }
}
