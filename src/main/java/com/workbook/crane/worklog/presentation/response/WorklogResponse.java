package com.workbook.crane.worklog.presentation.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.workbook.crane.worklog.application.Dto.WorklogDto;
import com.workbook.crane.worklog.application.model.info.WorklogInfo;
import com.workbook.crane.worklog.presentation.controller.WorklogController;
import java.security.Principal;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class WorklogResponse extends
    RepresentationModel<WorklogResponse> {

  private WorklogDto worklogDto;

  public static WorklogResponse from(WorklogInfo info) {
    WorklogResponse response = new WorklogResponse();
    response.worklogDto = WorklogDto.from(info);
    return response;
  }

  public void createLink(Long id, Principal principal) throws Exception {
    this.add(linkTo(methodOn(WorklogController.class)
        .getWorklogById(id, principal)).withSelfRel());
    this.add(linkTo(methodOn(WorklogController.class)
        .deleteWorklog(worklogDto.getId(), principal))
        .withRel("href"));
  }
}
