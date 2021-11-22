package com.workbook.crane.partner.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workbook.crane.partner.domain.model.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long>{

	@Query("SELECT p FROM Partner p "
			+ "WHERE p.id = (SELECT MAX(p2.id) FROM Partner p2 WHERE p2.deletedAt IS NULL)")
	Partner findTheNewestCreatedPartner();

	@Query("SELECT p FROM Partner p WHERE p.deletedAt IS NULL")
	List<Partner> findAllActivePartner(Pageable pageable);

	Partner findByCompanyName(String companyName);

	Partner findByIdAndDeletedAtIsNull(Long partnerId);
}
