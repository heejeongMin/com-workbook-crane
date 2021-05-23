package com.workbook.crane.worklogContext.application.Service;

import com.workbook.crane.worklogContext.application.Dto.WorklogDto;
import com.workbook.crane.worklogContext.domain.model.Worklog;
import com.workbook.crane.worklogContext.domain.repository.WorklogQueryRepository;
import com.workbook.crane.worklogContext.domain.repository.WorklogRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorklogService {

  private final WorklogRepository worklogRepository;
  private final WorklogQueryRepository worklogQueryRepository;

  public WorklogDto createWorklog(WorklogDto worklogDto) {
    Worklog workLog = worklogRepository.save(worklogDto.toEntity());
    return workLog.toDto();
  }

  @Transactional(readOnly = true)
  public WorklogDto searchWorklogById(Long id) {
    Optional<Worklog> worklog = worklogRepository.findById(id);
    return (worklog.isEmpty()) ? new WorklogDto() : worklog.get().toDto();//todo biz exception?
  }

  public List<WorklogDto> searchWorklogAll(
      LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
    return
        worklogQueryRepository
            .findWorklogAll(startDate, endDate, PageRequest.of(page, size))
            .stream()
            .map(Worklog::toDto)
            .collect(Collectors.toList());
  }

  @Transactional
  public List<WorklogDto> updateWorklogIFPerformed(List<Long> ids, boolean isPerformed) {
    return
        worklogRepository.findAllById(ids)
            .stream()
            .map(worklog -> {
              worklog.markWorklogIFPerformed(isPerformed);
              return worklog.toDto();
            }).collect(Collectors.toList());
  }

  @Transactional
  public List<WorklogDto> updateWorklogIFPaymentCollected(
      List<Long> ids, boolean isPaymentCollected) throws Exception {

    List<Worklog> worklogs = worklogRepository.findAllById(ids);
   if(!worklogs.stream().anyMatch(worklog -> worklog.isPerformed())) {
     throw new Exception("수행하지 않은 근무일정은 수금할 수 없습니다.");
   }

    return
        worklogRepository.findAllById(ids)
            .stream()
            .map(worklog -> {
              worklog.markWorklogIFPaymentCollected(isPaymentCollected);
              return worklog.toDto();
            }).collect(Collectors.toList());
  }

  @Transactional
  public List<WorklogDto> deleteWorklog(List<Long> ids) {
    return
        worklogRepository.findAllById(ids).stream().map(
            worklog -> {
              worklog.markWorklogAsDeleted();
              return worklog.toDto();
            }).collect(Collectors.toList());
  }
}
