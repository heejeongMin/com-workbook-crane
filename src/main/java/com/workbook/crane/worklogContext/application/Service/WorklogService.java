package com.workbook.crane.worklogContext.application.Service;

import com.workbook.crane.worklogContext.application.Dto.WorklogDto;
import com.workbook.crane.worklogContext.domain.model.Worklog;
import com.workbook.crane.worklogContext.domain.repository.WorklogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorklogService {

  private final WorklogRepository worklogRepository;

  public WorklogDto createWorklog(WorklogDto worklogDto){
    Worklog workLog = worklogRepository.save(worklogDto.toEntity());
    return workLog.toDto().setCalculatedTotal(workLog.calculateTotal());
  }

}
