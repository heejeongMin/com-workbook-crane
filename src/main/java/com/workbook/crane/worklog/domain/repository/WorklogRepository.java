package com.workbook.crane.worklog.domain.repository;

import com.workbook.crane.worklog.domain.model.Worklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorklogRepository extends
    JpaRepository<Worklog, Long>,
    PagingAndSortingRepository<Worklog, Long>,
    WorklogRepositoryCustom {

}
