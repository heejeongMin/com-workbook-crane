package com.workbook.crane.worklog.domain.repository;

import static com.workbook.crane.worklog.domain.model.QWorklog.worklog;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.application.model.criteria.WorklogSearchCriteria;
import com.workbook.crane.worklog.domain.model.Worklog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class WorklogRepositoryImpl
    extends QuerydslRepositorySupport
    implements WorklogRepositoryCustom {

  public WorklogRepositoryImpl() {
    super(Worklog.class);
  }

  @Override
  public Page<Worklog> findAllWorklogByCriteria(
      WorklogSearchCriteria criteria, Partner partner, User user) {

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(worklog.createdAt.goe(criteria.getCreatedAtFrom()))
        .and(worklog.createdAt.lt(criteria.getCreatedAtTo()))
        .and(worklog.deletedAt.isNull())
        .and(worklog.user.eq(user));

    if (partner != null) {
      builder.and(worklog.partner.eq(partner));
    }

    QueryResults<Worklog> result =
        from(worklog)
            .where(builder)
            .offset(criteria.getPageRequest().getOffset())
            .limit(criteria.getPageRequest().getPageSize())
            .fetchResults();

    return new PageImpl<>(result.getResults(), criteria.getPageRequest(), result.getTotal());
  }
}
