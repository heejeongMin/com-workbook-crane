package com.workbook.crane.worklog.domain.repository;

import com.workbook.crane.worklog.domain.model.Worklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorklogRepository extends JpaRepository<Worklog, Long> {
}
