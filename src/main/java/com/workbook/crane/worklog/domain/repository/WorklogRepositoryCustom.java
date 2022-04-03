package com.workbook.crane.worklog.domain.repository;

import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.application.model.criteria.WorklogSearchCriteria;
import com.workbook.crane.worklog.domain.model.Worklog;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorklogRepositoryCustom {

  Page<Worklog> findAllWorklogByCriteria(WorklogSearchCriteria criteria, Partner partner, User user);
}
