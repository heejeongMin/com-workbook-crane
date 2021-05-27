package com.workbook.crane.worklogContext.domain.repository;

import com.workbook.crane.worklogContext.domain.model.Worklog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorklogRepository extends JpaRepository<Worklog, Long> {
}
