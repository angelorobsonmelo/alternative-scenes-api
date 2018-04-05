package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.UserAppService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/users_app")
public class UserAppController {

  private static final Logger log = getLogger(UserAppController.class);

  @Autowired
  private UserAppService appService;

  @Value("${pagination.quantity_per_page}")
  private int quantityPerPage;

  public UserAppController() {
  }

  /**
   * Return list of users
   *
   * @return ResponseEntity<Response<UserAppDto>>
   */

  @GetMapping
  public ResponseEntity<Response<Page<UserAppDto>>> findAll(
    @RequestParam(value = "pag", defaultValue = "0") int pag,
    @RequestParam(value = "ord", defaultValue = "id") String ord,
    @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
    log.info("Searching for users page: {}", pag);
    Response<Page<UserAppDto>> response = new Response<>();

    PageRequest pageRequest = new PageRequest(pag, this.quantityPerPage, Sort.Direction.valueOf(dir), ord);
    Page<UserAppDto> userAppDtos = this.appService.findAll(pageRequest);

    response.setData(userAppDtos);
    return ResponseEntity.ok(response);
  }

}
