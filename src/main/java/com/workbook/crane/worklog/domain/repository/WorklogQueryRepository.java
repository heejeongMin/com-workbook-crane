package com.workbook.crane.worklog.domain.repository;

import static com.querydsl.jpa.JPAExpressions.selectFrom;
import static com.workbook.crane.worklog.domain.model.QWorklog.worklog;

import com.querydsl.core.QueryResults;
import com.workbook.crane.worklog.domain.model.Worklog;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class WorklogQueryRepository {

  public Page<Worklog> findWorklogAll(
      LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
    QueryResults<Worklog> result =
        selectFrom(worklog)
            .where(worklog.workPeriod.startDate.goe(startDate)
                .and(worklog.workPeriod.endDate.lt(endDate)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();
    return new PageImpl<>(result.getResults(), pageable, result.getTotal());
  }
}
