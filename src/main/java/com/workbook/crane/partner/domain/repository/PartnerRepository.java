package com.workbook.crane.partner.domain.repository;

import java.util.List;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.workbook.crane.partner.domain.model.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

  @Query("SELECT p FROM Partner p WHERE p.deletedAt IS NULL AND p.createdBy = :userId")
  List<Partner> findAllActivePartner(
      @Param(value = "userId") Long userId,
      Pageable pageable);

  Optional<Partner> findByCompanyNameContainsAndCreatedByAndDeletedAtIsNull(
      String companyName, Long createdBy);

  Partner findByPartnerNumberAndCreatedByAndDeletedAtIsNull(String partnerNumber, Long createdBy);

  Partner findByIdAndDeletedAtIsNull(Long id);

  int countAllByCreatedByAndDeletedAtIsNull(Long id);
}
