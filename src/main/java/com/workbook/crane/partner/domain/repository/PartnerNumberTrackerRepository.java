package com.workbook.crane.partner.domain.repository;

import com.workbook.crane.partner.domain.model.PartnerNumberTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerNumberTrackerRepository extends JpaRepository<PartnerNumberTracker, Long> {


}
