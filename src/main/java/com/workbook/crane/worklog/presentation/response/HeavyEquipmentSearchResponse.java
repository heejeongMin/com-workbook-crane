package com.workbook.crane.worklog.presentation.response;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.workbook.crane.worklog.application.Dto.HeavyEquipmentDto;
import com.workbook.crane.worklog.application.model.info.HeavyEquipmentInfo;
import com.workbook.crane.worklog.presentation.controller.HeavyEquipmentController;
import java.security.Principal;
import java.util.List;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class HeavyEquipmentSearchResponse extends
    RepresentationModel<HeavyEquipmentSearchResponse> {

  List<HeavyEquipmentDto> heavyEquipmentDtos;

  public static HeavyEquipmentSearchResponse from(List<HeavyEquipmentInfo> heavyEquipments) {
    HeavyEquipmentSearchResponse response = new HeavyEquipmentSearchResponse();
    response.heavyEquipmentDtos =
        heavyEquipments.stream()
            .map(HeavyEquipmentDto::from)
            .collect(toList());
    return response;
  }

  public void createLink(Principal principal) throws Exception {
    this.add(linkTo(methodOn(HeavyEquipmentController.class)
        .getAllHeavyEquipments(principal)).withSelfRel());
  }
}
