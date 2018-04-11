package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.dtos.UserAppSaveDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.UserAppService;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

import static com.angelorobson.alternativescene.utils.PasswordUtils.generateBCrypt;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.beans.BeanUtils.*;

@RestController
@RequestMapping("/users_app")
@CrossOrigin(origins = "http://localhost:8080")
public class UserAppController {

  private static final Logger log = getLogger(UserAppController.class);

  @Autowired
  private UserAppService userAppService;

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
    Page<UserAppDto> userAppDtos = this.userAppService.findAll(pageRequest);

    response.setData(userAppDtos);
    return ResponseEntity.ok(response);
  }


  /**
   * Adds a new user.
   *
   * @param userAppSaveDto
   * @param result
   * @return ResponseEntity<Response<UserAppDto>>
   */
  @PostMapping
  public ResponseEntity<Response<UserAppDto>> save(@Valid @RequestBody UserAppSaveDto userAppSaveDto,
                                                   BindingResult result) {
    log.info("Adding User: {}", userAppSaveDto.toString());
    Response<UserAppDto> response = new Response<>();
    validateUser(userAppSaveDto, result);
    UserApp userApp = this.convertDToToUser(userAppSaveDto, result);

    if (result.hasErrors()) {
      log.error("Error validating user: {}", result.getAllErrors());
      result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    UserAppDto userAppDto = this.userAppService.persist(userApp);
    response.setData(userAppDto);
    return ResponseEntity.ok(response);
  }

  /**
   * Remove a user by ID.
   *
   * @param id
   * @return ResponseEntity<Response<String>>
   */
  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
    log.info("Removendo lançamento: {}", id);
    Response<String> response = new Response<>();
    Optional<UserApp> userApp = this.userAppService.findById(id);

    if (!userApp.isPresent()) {
      log.info("Error removing because user ID: {} must be invalid.", id);
      response.getErrors().add("Error removing user. Record not found for id " + id);
      return ResponseEntity.badRequest().body(response);
    }

    this.userAppService.remove(id);
    return ResponseEntity.ok(new Response<>());
  }


  /**
   * Adds a new user.
   *
   * @param userAppSaveDto
   * @param result
   * @return ResponseEntity<Response<UserAppDto>>
   * @throws ParseException
   */
  @PutMapping
  public ResponseEntity<Response<UserAppDto>> update(@Valid @RequestBody UserAppSaveDto userAppSaveDto,
                                                   BindingResult result) throws ParseException {
    log.info("Editing user: {}", userAppSaveDto.toString());
    Response<UserAppDto> response = new Response<>();
    validateUser(userAppSaveDto, result);
    UserApp userApp = this.convertDToToUser(userAppSaveDto, result);

    if (result.hasErrors()) {
      log.error("Error validating user: {}", result.getAllErrors());
      result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    UserAppDto userAppDto = this.userAppService.edit(userApp);
    response.setData(userAppDto);
    return ResponseEntity.ok(response);
  }



  /**
   * Validate a user, verifying that it is existing and valid on the system.
   *
   * @param userAppSaveDto
   * @param result
   */
  private void validateUser(UserAppSaveDto userAppSaveDto, BindingResult result) {
    if (userAppSaveDto == null) {
      result.addError(new ObjectError("user", "User not informed."));
    }
  }

  /**
   * Convert a User to a entity.
   *
   * @param userAppSaveDto
   * @param result
   * @return UserApp
   */
  private UserApp convertDToToUser(UserAppSaveDto userAppSaveDto, BindingResult result) {
    UserApp userApp = new UserApp();

    if (userAppSaveDto.getId().isPresent()) {
      Optional<UserApp> user = this.userAppService.findById(userAppSaveDto.getId().get());
      if (user.isPresent()) {
        copyProperties(userAppSaveDto, user.get(), "email", "password", "id");
        userApp = user.get();
        return userApp;
      } else {
        result.addError(new ObjectError("user", "User not found."));
      }
    }

    userApp.setName(userAppSaveDto.getName());
    userApp.setEmail(userAppSaveDto.getEmail());
    userApp.setPassword(generateBCrypt(userAppSaveDto.getPassword()));
    userApp.setDateBirth(userAppSaveDto.getDateBirth());
    userApp.setImageUrl(userAppSaveDto.getImageUrl());
    userApp.setProfile(ProfileEnum.ROLE_USER);

    return userApp;
  }
}
