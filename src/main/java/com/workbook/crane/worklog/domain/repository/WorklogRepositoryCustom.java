package com.workbook.crane.worklog.domain.repository;

import com.workbook.crane.worklog.domain.model.Worklog;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorklogRepositoryCustom {

  Page<Worklog> findAllWorklog(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
