package com.workbook.crane.worklog.presentation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.workbook.crane.worklog.application.Dto.WorklogExcelDto;
import com.workbook.crane.worklog.application.Service.WorklogService;
import com.workbook.crane.worklog.presentation.request.WorklogCreateReq;
import com.workbook.crane.worklog.presentation.request.WorklogExcelReq;
import com.workbook.crane.worklog.presentation.response.WorklogCreateRes;
import com.workbook.crane.worklog.presentation.response.WorklogRes;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WorklogController {

  private final WorklogService worklogService;


  @GetMapping(value = "/crane")
  public String kubeTest() throws Exception {
    log.info("123");

    ApiClient client = Config.defaultClient();
    Configuration.setDefaultApiClient(client);

    CoreV1Api api = new CoreV1Api();
    V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, 10, false);
    nodeList.getItems()
        .stream()
        .forEach((node) -> System.out.println(node));

    String localHostAddress = InetAddress.getLocalHost().getHostAddress();
    String localHostName = InetAddress.getLocalHost().getHostName();

    String remoteHostAddress = InetAddress.getLoopbackAddress().getHostAddress();
    String remoteHostName = InetAddress.getLoopbackAddress().getHostName();

    return "Local : " + localHostAddress + "/" + localHostName + "/n"
        + "Remote : " + remoteHostAddress + "/" + remoteHostName;

  }

  @PostMapping(value = "/crane/v1/worklog")
  public ResponseEntity<EntityModel<WorklogCreateRes>> createWorklog(
      @RequestBody WorklogCreateReq worklogCreateReq) {
    return new ResponseEntity(
        new WorklogCreateRes(worklogService.createWorklog(worklogCreateReq.toDto())),
        HttpStatus.CREATED);
  }

  @GetMapping(value = "/crane/v1/worklog")
  public ResponseEntity<WorklogRes> searchAllWorklog(
      @RequestParam(value = "startDate")
      @DateTimeFormat(iso = ISO.DATE_TIME)
          LocalDateTime startDate,
      @RequestParam(value = "endDate", required = false)
      @DateTimeFormat(iso = ISO.DATE_TIME)
          LocalDateTime endDate,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "8") int size) {
    return ResponseEntity.ok(
        WorklogRes.from(worklogService.searchWorklogAll(startDate, endDate, page, size)));
  }

  @DeleteMapping(value = "/crane/v1/worklog")
  public ResponseEntity<WorklogRes> deleteWorklog(@RequestParam(value = "ids") List<Long> ids) {
    return ResponseEntity.ok(WorklogRes.from(worklogService.deleteWorklog(ids)));
  }

  @PostMapping(value = "/crane/v1/worklog/email")
  public ResponseEntity sendWorklogEmail(
      @Valid @RequestBody WorklogExcelReq worklogExcelReq) throws Exception {
    worklogService.sendWorklogEmail(WorklogExcelDto.from(worklogExcelReq));

    return ResponseEntity.ok(null);
  }
}
