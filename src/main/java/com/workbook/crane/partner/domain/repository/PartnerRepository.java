package com.workbook.crane.partner.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workbook.crane.partner.domain.model.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long>{
	Optional<Partner> findByUserId(Long userId);
	Optional<Partner> findByPartnerNumber(String partnerNumber);
}
