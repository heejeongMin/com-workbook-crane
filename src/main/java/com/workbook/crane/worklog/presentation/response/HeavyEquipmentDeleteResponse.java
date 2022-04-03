package com.workbook.crane.worklog.presentation.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.workbook.crane.worklog.application.Dto.HeavyEquipmentDto;
import com.workbook.crane.worklog.application.model.info.HeavyEquipmentInfo;
import com.workbook.crane.worklog.presentation.controller.HeavyEquipmentController;
import com.workbook.crane.worklog.presentation.request.HeavyEquipmentCreateRequest;
import java.security.Principal;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class HeavyEquipmentDeleteResponse extends
    RepresentationModel<HeavyEquipmentDeleteResponse> {

  HeavyEquipmentDto heavyEquipmentDto;

  public static HeavyEquipmentDeleteResponse from(HeavyEquipmentInfo heavyEquipments) {
    HeavyEquipmentDeleteResponse response = new HeavyEquipmentDeleteResponse();
    response.heavyEquipmentDto = HeavyEquipmentDto.from(heavyEquipments);
    return response;
  }

  public void createLink(Long id, Principal principal) throws Exception {
    this.add(linkTo(methodOn(HeavyEquipmentController.class)
        .deleteHeavyEquipment(id, principal)).withSelfRel());
  }
}
