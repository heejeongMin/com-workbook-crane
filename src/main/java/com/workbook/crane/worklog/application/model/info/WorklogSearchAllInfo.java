package com.workbook.crane.worklog.application.model.info;

import static java.util.stream.Collectors.toList;

import com.workbook.crane.worklog.domain.model.Worklog;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class WorklogSearchAllInfo {

  private List<WorklogInfo> worklogInfos;
  private long totalItems;
  private long totalPages;

  public static WorklogSearchAllInfo from(Page<Worklog> pages) {
    WorklogSearchAllInfo info = new WorklogSearchAllInfo();
    info.worklogInfos =
        pages.getContent().stream()
            .map(WorklogInfo::from)
            .collect(toList());
    info.totalItems = pages.getTotalElements();
    info.totalPages = pages.getTotalPages();
    return info;
  }

  public static WorklogSearchAllInfo getDefault(){
    WorklogSearchAllInfo info = new WorklogSearchAllInfo();
    info.worklogInfos = Collections.emptyList();
    return info;
  }
}
