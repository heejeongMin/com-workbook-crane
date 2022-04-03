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
public class HeavyEquipmentCreateResponse extends
    RepresentationModel<HeavyEquipmentCreateResponse> {

  HeavyEquipmentDto heavyEquipmentDto;

  public static HeavyEquipmentCreateResponse from(HeavyEquipmentInfo heavyEquipments) {
    HeavyEquipmentCreateResponse response = new HeavyEquipmentCreateResponse();
    response.heavyEquipmentDto = HeavyEquipmentDto.from(heavyEquipments);
    return response;
  }

  public void createLink(HeavyEquipmentCreateRequest request, Principal principal) throws Exception {
    this.add(linkTo(methodOn(HeavyEquipmentController.class)
        .createHeavyEquipment(request, principal)).withSelfRel());
    this.add(linkTo(methodOn(HeavyEquipmentController.class)
        .getAllHeavyEquipments(principal)).withRel("href"));
  }
}
