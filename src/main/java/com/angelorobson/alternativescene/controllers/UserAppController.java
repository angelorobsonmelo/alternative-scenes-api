package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.dtos.UserAppEditDto;
import com.angelorobson.alternativescene.dtos.UserAppSaveDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.UserAppService;
import org.slf4j.Logger;
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
import java.time.LocalDate;
import java.util.Optional;

import static com.angelorobson.alternativescene.converters.Converters.convertUserAppEntityToDto;
import static com.angelorobson.alternativescene.utils.PasswordUtils.generateBCrypt;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users_app")
@CrossOrigin(origins = "*")
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
     * @return ResponseEntity<Response < Page < UserAppDto>>>
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

    @GetMapping("/findByEmailAndAndGoogleAccountId")
    public ResponseEntity<Response<UserAppDto>> findByEmailAndGoogleAccountId(
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "googleAccountId", defaultValue = "") String googleAcountId) {
        log.info("Searching for users by email: {}", email);
        Response<UserAppDto> response = new Response<>();
        Optional<UserApp> userApp = this.userAppService.findByEmailAndGoogleAccountId(email, googleAcountId);

        userApp.ifPresent(user -> {
            UserAppDto userAppDto = convertUserAppEntityToDto(user);
            response.setData(userAppDto);
        });

        return response.getData().getId() != null ? ok(response) : notFound().build();
    }


    /**
     * Adds a new user.
     *
     * @param userAppSaveDto
     * @param result
     * @return ResponseEntity<Response < UserAppDto>>
     */
    @PostMapping
    public ResponseEntity<Response<UserAppDto>> save(@Valid @RequestBody UserAppSaveDto userAppSaveDto,
                                                     BindingResult result) {
        log.info("Adding User: {}", userAppSaveDto.toString());
        Response<UserAppDto> response = new Response<>();
        validateUser(userAppSaveDto, result);
        UserApp userApp = this.convertUserSaveAppDToToUserAppROLERUSER(userAppSaveDto);

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
     * @return ResponseEntity<Response < String>>
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
     * Updates a user's data.
     *
     * @param id
     * @param userAppEditDto
     * @return ResponseEntity<Response < Lancamento>>
     * @throws ParseException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<UserAppDto>> update(@PathVariable("id") Long id,
                                                       @Valid @RequestBody UserAppEditDto userAppEditDto, BindingResult result) {
        log.info("Updates a user's data.: {}", userAppEditDto.toString());
        Response<UserAppDto> response = new Response<>();
        validateUser(userAppEditDto, result);
        userAppEditDto.setId(of(id));
        UserApp userApp = this.convertUserEditAppDToToUserApp(userAppEditDto, result);

        if (result.hasErrors()) {
            log.error("Error validating user: {}", result.getAllErrors());
            if (result.hasErrors()) {
                log.error("Error validating user: {}", result.getAllErrors());
                result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }

        }

        UserAppDto userAppDto = this.userAppService.edit(userApp);
        response.setData(userAppDto);
        return ResponseEntity.ok(response);
    }


    /**
     * Validate a user, verifying that it is existing and valid on the system.
     *
     * @param o
     * @param result
     */
    private void validateUser(Object o, BindingResult result) {
        if (o == null) {
            result.addError(new ObjectError("user", "User not informed."));
        }
    }

    /**
     * Convert a User save dto to a entity.
     *
     * @param userAppSaveDto
     * @return UserApp
     */
    private UserApp convertUserSaveAppDToToUserAppROLERUSER(UserAppSaveDto userAppSaveDto) {
        UserApp userApp = new UserApp();
        userApp.setName(userAppSaveDto.getName());
        userApp.setEmail(userAppSaveDto.getEmail());
        userApp.setPassword(generateBCrypt(userAppSaveDto.getPassword()));
        userApp.setDateBirth(LocalDate.now());
        userApp.setImageUrl(userAppSaveDto.getImageUrl());
        userApp.setProfile(ProfileEnum.ROLE_USER);
        userApp.setPhoneNumber("");
        userApp.setGender(userAppSaveDto.getGender());
        userApp.setGoogleAccountId(userAppSaveDto.getGoogleAccountId());

        return userApp;
    }

    /**
     * Convert a User edit dto to a entity.
     *
     * @param userAppEditDto
     * @param result
     * @return UserApp
     */
    private UserApp convertUserEditAppDToToUserApp(UserAppEditDto userAppEditDto, BindingResult result) {
        UserApp userApp = new UserApp();

        if (userAppEditDto.getId().isPresent()) {
            Optional<UserApp> user = this.userAppService.findById(userAppEditDto.getId().get());
            if (user.isPresent()) {
                if (userAppEditDto.getPassword() != null) {
                    user.get().setPassword(generateBCrypt(userAppEditDto.getPassword()));
                }

                copyProperties(userAppEditDto, user.get(), "email", "id", "password");

                userApp = user.get();
                return userApp;
            } else {
                result.addError(new ObjectError("user", "User not found."));
            }
        }

        return userApp;
    }
}
