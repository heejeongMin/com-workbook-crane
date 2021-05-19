package com.workbook.crane.worklogContext.presenation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.workbook.crane.worklogContext.application.Service.WorklogService;
import com.workbook.crane.worklogContext.presenation.request.WorklogCreateReq;
import com.workbook.crane.worklogContext.presenation.response.WorklogCreateRes;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WorklogController {

  private final WorklogService worklogService;

  @PostMapping("/crane/v1/worklog")
  public ResponseEntity<EntityModel<WorklogCreateRes>> createWorklog(
      @RequestBody WorklogCreateReq worklogCreateReq){

    WorklogCreateRes res = new WorklogCreateRes(worklogService.createWorklog(worklogCreateReq.toDto()));

    URI createdURI = linkTo(WorklogController.class).slash(res.getWorklogDto()).toUri();
    EntityModel<WorklogCreateRes> entityModel = EntityModel.of(
        res,
        linkTo(WorklogController.class).slash(res.getWorklogDto()).withSelfRel(),
        linkTo(WorklogController.class).slash(res.getWorklogDto()).withRel("get"),
        linkTo(WorklogController.class).slash(res.getWorklogDto()).withRel("delete"),
        linkTo(WorklogController.class).slash(res.getWorklogDto()).withRel("edit"));

    return ResponseEntity.created(createdURI).body(entityModel);
//    return new ResponseEntity<>(
//        new WorklogCreateRes(worklogService.createWorklog(worklogCreateReq.toDto())),
//        HttpStatus.CREATED);
  }
}
