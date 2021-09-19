package com.workbook.crane.worklog.domain.repository;

import com.workbook.crane.worklog.domain.model.HeavyEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeavyEquipmentRepository extends JpaRepository<HeavyEquipment, Long> {

}
