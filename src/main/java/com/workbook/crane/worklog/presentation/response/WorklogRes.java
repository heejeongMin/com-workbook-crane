package com.workbook.crane.worklog.presentation.response;

import com.workbook.crane.worklog.application.Dto.WorklogDto;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class WorklogRes {
  private List<WorklogDto> worklogDtoList;
  private long totalItems;

  public static WorklogRes from(Map<String, Object> map) {
    WorklogRes res = new WorklogRes();
    res.worklogDtoList = (List<WorklogDto>) map.get("worklogDtoList");
    res.totalItems = (Long) map.get("totalItems");
    return res;
  }
}
