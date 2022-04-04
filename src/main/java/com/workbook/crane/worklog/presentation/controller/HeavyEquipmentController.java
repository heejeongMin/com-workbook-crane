package com.workbook.crane.worklog.presentation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.workbook.crane.worklog.application.Service.HeavyEquipmentService;
import com.workbook.crane.worklog.application.model.command.HeavyEquipmentCreateCommand;
import com.workbook.crane.worklog.presentation.request.HeavyEquipmentCreateRequest;
import com.workbook.crane.worklog.presentation.response.HeavyEquipmentCreateResponse;
import com.workbook.crane.worklog.presentation.response.HeavyEquipmentDeleteResponse;
import com.workbook.crane.worklog.presentation.response.HeavyEquipmentSearchResponse;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HeavyEquipmentController {

  private final HeavyEquipmentService heavyEquipmentService;

  @GetMapping("/crane/v1/heavyEquipment")
  public ResponseEntity<HeavyEquipmentSearchResponse> getAllHeavyEquipments(Principal principal)
      throws Exception {
    HeavyEquipmentSearchResponse response =
        HeavyEquipmentSearchResponse.from(
            heavyEquipmentService.getAllHeavyEquipments(principal.getName()));
    response.createLink(principal);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/crane/v1/heavyEquipment")
  public ResponseEntity<HeavyEquipmentCreateResponse> createHeavyEquipment(
      @Valid @RequestBody HeavyEquipmentCreateRequest request, Principal principal)
      throws Exception {
    HeavyEquipmentCreateResponse response =
        HeavyEquipmentCreateResponse.from(
            heavyEquipmentService.createHeavyEquipment(
                HeavyEquipmentCreateCommand.of(request, principal.getName())));
    response.createLink(request, principal);

    return ResponseEntity.created(
            linkTo(HeavyEquipmentController.class).slash(response.getHeavyEquipmentDto().getId()).toUri())
        .body(response);
  }

  @DeleteMapping("/crane/v1/heavyEquipment/{id}")
  public ResponseEntity<HeavyEquipmentDeleteResponse> deleteHeavyEquipment(
      @PathVariable(value = "id") Long id, Principal principal) throws Exception {
    HeavyEquipmentDeleteResponse response =
        HeavyEquipmentDeleteResponse.from(
            heavyEquipmentService.deleteHeavyEquipment(id, principal.getName()));
    response.createLink(id, principal);

    return ResponseEntity.ok(response);
  }
}
