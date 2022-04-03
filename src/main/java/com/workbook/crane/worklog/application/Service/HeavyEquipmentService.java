package com.workbook.crane.worklog.application.Service;

import com.workbook.crane.user.application.service.AuthService;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.application.model.command.HeavyEquipmentCreateCommand;
import com.workbook.crane.worklog.application.model.info.HeavyEquipmentInfo;
import com.workbook.crane.worklog.domain.model.HeavyEquipment;
import com.workbook.crane.worklog.domain.repository.HeavyEquipmentRepository;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeavyEquipmentService {

  private final AuthService authService;
  private final HeavyEquipmentRepository heavyEquipmentRepository;

  @Transactional(readOnly = true)
  public List<HeavyEquipmentInfo> getAllHeavyEquipments(String username) throws Exception {
    User user = authService.getUserOrElseThrow(username);
    List<HeavyEquipment> heavyEquipments =
        heavyEquipmentRepository.findByUserAndDeletedAtIsNull(user);

    return HeavyEquipmentInfo.from(heavyEquipments);
  }

  @Transactional
  public HeavyEquipmentInfo createHeavyEquipment(HeavyEquipmentCreateCommand command)
      throws Exception {
    User user = authService.getUserOrElseThrow(command.getUsername());

    HeavyEquipment heavyEquipment = HeavyEquipment.create(command, user);
    heavyEquipment = heavyEquipmentRepository.save(heavyEquipment);

    return HeavyEquipmentInfo.from(heavyEquipment);
  }

  @Transactional
  public HeavyEquipmentInfo deleteHeavyEquipment(Long id, String username) throws Exception {
    User user = authService.getUserOrElseThrow(username);
    HeavyEquipment heavyEquipment = this.getHeavyEquipmentOrElseThrow(id, user);
    heavyEquipment.markAsDeleted();
    return HeavyEquipmentInfo.from(heavyEquipment);
  }

  public HeavyEquipment getHeavyEquipmentOrElseThrow(Long equipmentId, User user) throws Exception {
    HeavyEquipment equipment =
        heavyEquipmentRepository.findByIdEqualsAndUserAndDeletedAtIsNull(equipmentId, user);
    if (equipment == null) {
      throw new Exception("Equipment does not exist");
    }
    return equipment;
  }
}
