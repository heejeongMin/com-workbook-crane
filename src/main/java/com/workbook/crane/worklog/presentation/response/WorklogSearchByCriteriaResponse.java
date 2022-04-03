package com.workbook.crane.worklog.presentation.response;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.workbook.crane.partner.presentation.controller.PartnerController;
import com.workbook.crane.partner.presentation.response.PartnerSearchByPartnerNumberResponse;
import com.workbook.crane.worklog.application.Dto.WorklogDto;
import com.workbook.crane.worklog.application.model.info.WorklogSearchAllInfo;
import com.workbook.crane.worklog.presentation.controller.WorklogController;
import com.workbook.crane.worklog.presentation.request.WorklogSearchCriteriaRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class WorklogSearchByCriteriaResponse extends
    RepresentationModel<WorklogSearchByCriteriaResponse> {

  private List<WorklogDto> worklogDtos;
  private long totalItems;
  private long totalPages;

  public static WorklogSearchByCriteriaResponse from(WorklogSearchAllInfo infos) {
    WorklogSearchByCriteriaResponse response = new WorklogSearchByCriteriaResponse();
    response.worklogDtos =
        infos.getWorklogInfos().stream()
            .map(WorklogDto::from)
            .collect(toList());
    response.totalItems = infos.getTotalItems();
    response.totalPages = infos.getTotalPages();
    return response;
  }

  public void createLink(
      LocalDateTime startedAt,
      LocalDateTime finishedAt,
      String partnerName,
      int page,
      int size,
      Principal principal)
      throws Exception {
    this.add(linkTo(methodOn(WorklogController.class)
        .searchAllWorklog(
            startedAt, finishedAt, partnerName, page, size, principal)).withSelfRel());

    this.getWorklogDtos()
        .forEach(worklogDto -> {
              try {
                this.add(linkTo(methodOn(WorklogController.class)
                    .getWorklogById(worklogDto.getId(), principal))
                    .withRel("href"));
                this.add(linkTo(methodOn(WorklogController.class)
                    .deleteWorklog(worklogDto.getId(), principal))
                    .withRel("href"));
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
        );
  }
}
