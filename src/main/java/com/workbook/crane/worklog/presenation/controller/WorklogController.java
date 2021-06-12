package com.workbook.crane.worklog.presenation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.workbook.crane.worklog.application.Service.WorklogService;
import com.workbook.crane.worklog.presenation.request.WorklogCreateReq;
import com.workbook.crane.worklog.presenation.response.WorklogCreateRes;
import com.workbook.crane.worklog.presenation.response.WorklogRes;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

  @PostMapping(value = "/crane/v1/worklog"
//      consumes = "application/json-patch+json"
  )
  public ResponseEntity<EntityModel<WorklogCreateRes>> createWorklog(
      @RequestBody WorklogCreateReq worklogCreateReq){

    WorklogCreateRes res = new WorklogCreateRes(worklogService.createWorklog(worklogCreateReq.toDto()));

    URI createdURI = linkTo(WorklogController.class).slash(res.getWorklogDto().getId()).toUri();
    EntityModel<WorklogCreateRes> entityModel = EntityModel.of(
        res,
        linkTo(WorklogController.class).slash(res.getWorklogDto().getId()).withSelfRel(),
        linkTo(WorklogController.class).slash(res.getWorklogDto().getId()).withRel("get"),
        linkTo(WorklogController.class).slash(res.getWorklogDto().getId()).withRel("delete"),
        linkTo(WorklogController.class).slash(res.getWorklogDto().getId()).withRel("edit"));

    return ResponseEntity.created(createdURI).body(entityModel);
//    return new ResponseEntity<>(
//        new WorklogCreateRes(worklogService.createWorklog(worklogCreateReq.toDto())),
//        HttpStatus.CREATED);
  }

  @GetMapping(value = "/crane/v1/worklog")
  public ResponseEntity<WorklogRes> searchAllWorklog(
      @RequestParam(value = "startDate")
      @DateTimeFormat(iso = ISO.DATE_TIME)
          LocalDateTime startDate,
      @RequestParam(value = "endDate", required = false)
      @DateTimeFormat(iso = ISO.DATE_TIME)
          LocalDateTime endDate,
      @RequestParam(value =  "page", defaultValue = "1") int page,
      @RequestParam(value =  "size", defaultValue = "1") int size){
    return ResponseEntity.ok(
        new WorklogRes(worklogService.searchWorklogAll(startDate, endDate, page, size)));
  }

  @GetMapping(value = "/crane/v1/worklog/{id}")
  public ResponseEntity<WorklogRes> searchWorklogById(@PathVariable(value = "id") Long id){
    return ResponseEntity.ok(
        new WorklogRes(Arrays.asList(worklogService.searchWorklogById(id))));
  }

  @PatchMapping(value = "/crane/v1/worklog/performed")
  public ResponseEntity<WorklogRes> updateWorklogPerformed(
      @RequestParam(value = "ids") List<Long> ids,
      @RequestParam(value = "isPerformed") boolean isPerformed){
    return ResponseEntity.ok(
        new WorklogRes(worklogService.updateWorklogIFPerformed(ids, isPerformed)));
  }

  @PatchMapping(value = "/crane/v1/worklog/payment-collected")
  public ResponseEntity<WorklogRes> updateWorklogPaymentCollected(
      @RequestParam(value = "ids") List<Long> ids,
      @RequestParam(value = "isPaymentCollected") boolean isPaymentCollected) throws Exception {
    return ResponseEntity.ok(
        new WorklogRes(worklogService.updateWorklogIFPaymentCollected(ids, isPaymentCollected)));
  }

  @DeleteMapping(value = "/crane/v1/worklog")
  public ResponseEntity<WorklogRes> deleteWorklog(@RequestParam(value = "ids") List<Long> ids) {
    return ResponseEntity.ok(new WorklogRes(worklogService.deleteWorklog(ids)));
  }
}
