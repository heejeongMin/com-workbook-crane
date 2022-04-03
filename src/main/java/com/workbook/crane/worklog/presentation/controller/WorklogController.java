package com.workbook.crane.worklog.presentation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.workbook.crane.worklog.application.Dto.WorklogExcelDto;
import com.workbook.crane.worklog.application.Service.WorklogService;
import com.workbook.crane.worklog.application.model.command.WorklogCreateCommand;
import com.workbook.crane.worklog.application.model.criteria.WorklogSearchCriteria;
import com.workbook.crane.worklog.presentation.request.WorklogCreateRequest;
import com.workbook.crane.worklog.presentation.request.WorklogExcelReq;
import com.workbook.crane.worklog.presentation.request.WorklogSearchCriteriaRequest;
import com.workbook.crane.worklog.presentation.response.WorklogCreateResponse;
import com.workbook.crane.worklog.presentation.response.WorklogSearchByCriteriaResponse;
import com.workbook.crane.worklog.presentation.response.WorklogResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WorklogController {

  private final WorklogService worklogService;

  @PostMapping(value = "/crane/v1/worklog", produces = {"application/hal+json"})
  public ResponseEntity<WorklogCreateResponse> createWorklog(
      @Valid @RequestBody WorklogCreateRequest worklogCreateRequest,
      Principal principal) throws Exception {
    WorklogCreateResponse response =
        WorklogCreateResponse.from(
            worklogService.createWorklog(
                WorklogCreateCommand.of(worklogCreateRequest, principal.getName())));

    response.createLink(worklogCreateRequest, principal);

    return ResponseEntity.created(
            linkTo(WorklogController.class).slash(response.getId()).toUri())
        .body(response);
  }

  @GetMapping(value = "/crane/v1/worklog", produces = {"application/hal+json"})
  public ResponseEntity<WorklogSearchByCriteriaResponse> searchAllWorklog(
      @RequestParam(value = "startedAt")
      @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startedAt,
      @RequestParam(value = "finishedAt", required = false)
      @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime finishedAt,
      @RequestParam(value = "partnerName", required = false) String partnerName,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "8") int size,
      Principal principal)
      throws Exception {

    WorklogSearchByCriteriaResponse response =
        WorklogSearchByCriteriaResponse.from(
            worklogService.searchAllWorklog(WorklogSearchCriteria.of(
                startedAt, finishedAt, partnerName, page, size, principal.getName())));

    response.createLink(startedAt, finishedAt, partnerName, page, size, principal);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/crane/v1/worklog/{id}", produces = {"application/hal+json"})
  public ResponseEntity<WorklogResponse> getWorklogById(
      @PathVariable(value = "id") Long id, Principal principal) throws Exception {
    WorklogResponse response =
        WorklogResponse.from(worklogService.getWorklogById(id, principal.getName()));
    response.createLink(id, principal);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(value = "/crane/v1/worklog/{id}")
  public ResponseEntity<WorklogResponse> deleteWorklog(
      @PathVariable(value = "id") Long id, Principal principal) throws Exception {
    return ResponseEntity.ok(
        WorklogResponse.from(worklogService.deleteWorklog(id, principal.getName())));
  }

  @PostMapping(value = "/crane/v1/worklog/email")
  public ResponseEntity sendWorklogEmail(
      @Valid @RequestBody WorklogExcelReq worklogExcelReq, Principal principal) throws Exception {
    worklogService.sendWorklogEmail(WorklogExcelDto.from(worklogExcelReq, principal.getName()));

    return ResponseEntity.ok(null);
  }
}
