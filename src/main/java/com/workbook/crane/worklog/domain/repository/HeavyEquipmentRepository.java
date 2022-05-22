package com.workbook.crane.worklog.domain.repository;

import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.domain.model.HeavyEquipment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeavyEquipmentRepository extends JpaRepository<HeavyEquipment, Long> {

  List<HeavyEquipment> findByUserAndDeletedAtIsNull(User user);

  HeavyEquipment findByIdEqualsAndUserAndDeletedAtIsNull(Long id, User user);

  int countAllByUserAndDeletedAtIsNull(User user);
}
