package com.workbook.crane.worklog.domain.repository;

import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.domain.model.Worklog;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
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

  @Query(
      "SELECT w FROM Worklog w "
          + "WHERE w.createdAt >= :from AND w.createdAt <= :to "
          + "AND w.user = :user "
          + "AND w.deletedAt IS NULL "
          + "ORDER BY w.createdAt ASC ")
  List<Worklog> findWorklogInGivenPeriod(
      @Param(value = "from") LocalDateTime from,
      @Param(value = "to") LocalDateTime to,
      @Param(value = "user") User user);

  Worklog findByIdAndUser(Long id, User user);
}
