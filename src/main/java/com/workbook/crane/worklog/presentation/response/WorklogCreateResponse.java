package com.workbook.crane.worklog.presentation.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.workbook.crane.worklog.application.model.info.WorklogCreateInfo;
import com.workbook.crane.worklog.domain.model.WorkTime;
import com.workbook.crane.worklog.presentation.controller.WorklogController;
import com.workbook.crane.worklog.presentation.request.WorklogCreateRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@RequiredArgsConstructor
public class WorklogCreateResponse extends RepresentationModel<WorklogCreateResponse> {
  private Long id;
  private Long equipmentId;
  private String location;
  private LocalDate workDate;
  private WorkTime workTime;
  private Double workPay;
  private Long partnerId;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  public static WorklogCreateResponse from(WorklogCreateInfo info){
    WorklogCreateResponse response = new WorklogCreateResponse();
    response.id = info.getId();
    response.equipmentId = info.getEquipmentId();
    response.location = info.getLocation();
    response.workDate = info.getWorkDate();
    response.workTime = info.getWorkTime();
    response.workPay = info.getWorkPay();
    response.partnerId = info.getPartnerId();
    response.createdAt = info.getCreatedAt();
    response.deletedAt = info.getDeletedAt();
    return response;
  }

  public void createLink(WorklogCreateRequest request, Principal principal) throws Exception {
    this.add(linkTo(methodOn(WorklogController.class)
        .createWorklog(request, principal)).withSelfRel());
//    this.add(linkTo(methodOn(WorklogController.class)
//        .searchPartnerByPartnerNumber(partnerNumber, principal)).withRel("href"));
//    this.add(linkTo(methodOn(WorklogController.class)
//        .searchAllPartner(1, 10, principal)).withRel("href"));
  }
}
