package com.angelorobson.alternativescene.controllers;


import com.angelorobson.alternativescene.converters.Converters;
import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.dtos.EventSaveDto;
import com.angelorobson.alternativescene.entities.*;
import com.angelorobson.alternativescene.entities.notification.Sender;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import com.angelorobson.alternativescene.enums.StatusEnum;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.angelorobson.alternativescene.converters.Converters.convertEventEntityToDto;
import static com.angelorobson.alternativescene.converters.Converters.converterEventSaveDtoToEntity;
import static org.springframework.data.domain.Sort.Direction.valueOf;
import static org.springframework.http.ResponseEntity.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/events")
public class EventController {

    private EventService eventService;
    private CityService cityService;
    private UserAppService userAppService;
    private UserDeviceService userDeviceService;
    private StateSerivice stateSerivice;

    @Autowired
    public EventController(EventService eventService,
                           CityService cityService,
                           UserAppService userAppService,
                           UserDeviceService userDeviceService,
                           StateSerivice stateSerivice) {
        this.eventService = eventService;
        this.cityService = cityService;
        this.userAppService = userAppService;
        this.userDeviceService = userDeviceService;
        this.stateSerivice = stateSerivice;
    }

    @PostMapping(value = "/findAll")
    public ResponseEntity<Response<Page<EventDto>>> findAll(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestParam(value = "perPage", defaultValue = "25") String perPage) {
        EventFilter eventFilter = new EventFilter();
        eventFilter.setStatus(StatusEnum.APPROVED);
        Response<Page<EventDto>> response = new Response<>();

        PageRequest pageRequest = PageRequest.of(pag, Integer.parseInt(perPage), valueOf(dir), ord);

        Page<EventDto> eventsReturned = this.eventService.findAllByFilter(eventFilter, pageRequest);

        response.setData(eventsReturned);
        return ok(response);
    }


    @PostMapping(value = "/findAllByAdmin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<EventDto>>> findAllByAdmin(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestParam(value = "perPage", defaultValue = "25") String perPage) {
        EventFilter eventFilter = new EventFilter();
        eventFilter.setStatus(StatusEnum.PENDING);
        Response<Page<EventDto>> response = new Response<>();

        PageRequest pageRequest = PageRequest.of(pag, Integer.parseInt(perPage), valueOf(dir), ord);

        Page<EventDto> eventsReturned = this.eventService.findAllByFilter(eventFilter, pageRequest);

        response.setData(eventsReturned);
        return ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<EventDto>> save(@RequestBody EventSaveDto eventSaveDto,
                                                   BindingResult result) throws IOException {
        Response<EventDto> response = new Response<>();
        Optional<City> cityReturned = cityService.findByName(eventSaveDto.getCityName());

        Optional<UserApp> userAppReturned = userAppService.findById(eventSaveDto.getUserAppId());

        if (canSave(cityReturned, userAppReturned)) {
            ResponseEntity<String> responseImageServerUpload = saveImage(eventSaveDto);
            if (responseImageServerUpload.getStatusCodeValue() == 200) {
                Event event = saveEventWithImageUrl(eventSaveDto, cityReturned.get(), responseImageServerUpload);
                Locality locality = event.getLocality();
                City city = locality.getCity();
                State state = city.getState();

                Optional<State> stateReturned = stateSerivice.findOne(state.getId());
                event.getLocality().getCity().setState(stateReturned.get());


                EventDto eventDto = convertEventEntityToDto(event);
                response.setData(eventDto);
                sendNotificationByUserDevice(eventDto, ProfileEnum.ROLE_ADMIN);

                return ok(response);
            }
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return badRequest().body(response);
        }

        return status(HttpStatus.BAD_REQUEST).build();
    }

    private boolean canSave(Optional<City> cityReturned, Optional<UserApp> userAppReturned) {
        return cityReturned.isPresent() && userAppReturned.isPresent() && userAppReturned.get().getActivated();
    }

    private ResponseEntity<String> saveImage(@RequestBody EventSaveDto eventSaveDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("image", eventSaveDto.getImageUrl());

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        String serverUrl = "https://api.imgbb.com/1/upload?key=ce212bfaaa6d25bc1857a69a6b2da17e";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .postForEntity(serverUrl, requestEntity, String.class);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<EventDto>> findOne(@PathVariable("id") Long id) {
        Response<EventDto> response = new Response<>();

        Optional<EventDto> eventDtoReturned = this.eventService.findOne(id);
        eventDtoReturned.ifPresent(response::setData);

        return response.getData().getId() != null ? ok(response) : notFound().build();
    }

    @GetMapping(value = "/getBy/{id}/{userId}/{status}")
    public ResponseEntity<Response<EventDto>> findOne(@PathVariable("id") Long id,
                                                      @PathVariable("userId") Long userId,
                                                      @PathVariable("status") Boolean status) {
        Response<EventDto> response = new Response<>();

        Optional<EventDto> eventDtoReturned = this.eventService.findByIdAndUserAppIdAndStatus(id, userId, status);
        if (eventDtoReturned.isPresent()) {
            response.setData(eventDtoReturned.get());
            return ok(response);
        }

        return notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
        Response<String> response = new Response<>();
        Optional<EventDto> eventDtoReturned = this.eventService.findOne(id);

        if (!eventDtoReturned.isPresent()) {
            response.getErrors().add("Error removing. Record not found for id " + id);
            return badRequest().body(response);
        }

        this.eventService.remove(id);
        return ok(new Response<>());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EventDto>> update(@PathVariable("id") Long id,
                                                     @RequestBody EventSaveDto eventSaveDto, BindingResult result) {
        Response<EventDto> response = new Response<>();
        eventSaveDto.setId(Optional.of(id));

        Event event = convertEventSaveDtoToEntity(eventSaveDto, result);
        event = this.eventService.save(event);

        response.setData(convertEventEntityToDto(event));
        return ok(response);
    }

    @PutMapping(value = "/approvedOrReprove/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<EventDto>> approvedOrReprove(@PathVariable("id") Long id,
                                                                   @RequestParam(value = "status") String status) {
        Response<EventDto> response = new Response<>();
        EventSaveDto eventSaveDto = new EventSaveDto();
        eventSaveDto.setId(Optional.of(id));

        Event event = eventService.findEventBy(id).get();
        StatusEnum statusEnum = Enum.valueOf(StatusEnum.class, status);

        event.setStatus(statusEnum);

        event = this.eventService.save(event);

        EventDto eventDto = convertEventEntityToDto(event);
        sendNotificationByUserDevice(eventDto, ProfileEnum.ROLE_USER);
        response.setData(eventDto);

        return ok(response);
    }

    @PostMapping(value = "/findAllByUserId")
    public ResponseEntity<Response<Page<EventDto>>> findAllByUserId(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestParam(value = "perPage", defaultValue = "25") String perPage) {
        EventFilter eventFilter = new EventFilter();
        eventFilter.setStatus(StatusEnum.APPROVED);
        Response<Page<EventDto>> response = new Response<>();

        PageRequest pageRequest = PageRequest.of(pag, Integer.parseInt(perPage), valueOf(dir), ord);

        Page<EventDto> eventsReturned = this.eventService.findAllByUser(eventFilter, pageRequest, userId);
        response.setData(eventsReturned);
        return ok(response);
    }

    private Event saveEventWithImageUrl(EventSaveDto eventSaveDto, City cityReturned, ResponseEntity<String> responseImageServerUpload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseImageServerUpload.getBody());
        String imageUrl = rootNode.path("data").path("url").asText();
        String thumbUrl = rootNode.path("data").path("thumb").path("url").asText();

        eventSaveDto.setImageUrl(imageUrl);
        eventSaveDto.setImageThumbUrl(thumbUrl);
        Event event = converterEventSaveDtoToEntity(eventSaveDto, cityReturned);
        event = this.eventService.save(event);
        return event;
    }

    private Event convertEventSaveDtoToEntity(EventSaveDto eventSaveDto, BindingResult result) {
        if (eventSaveDto.getId().isPresent()) {
            Optional<Event> eventReturned = this.eventService.findEventBy(eventSaveDto.getId().get());
            if (eventReturned.isPresent()) {
                Locality locality = eventReturned.get().getLocality();
                City city = locality.getCity();
                Event toEntity = Converters.converterEventSaveDtoToEntity(eventSaveDto, city);
                toEntity.setId(eventReturned.get().getId());
                toEntity.setRegistrationDate(eventReturned.get().getRegistrationDate());

                return toEntity;
            } else {
                result.addError(new ObjectError("user", "User not found."));
            }
        }

        return new Event();
    }


    private void sendNotificationByUserDevice(EventDto eventDto, ProfileEnum profileEnum) {
        List<UserDevice> userDevices = userDeviceService.findAllByUserProfile(profileEnum);
        List<String> devicesIds = userDevices.stream().map(UserDevice::getDeviceId).collect(Collectors.toList());

        sendNotification(eventDto, devicesIds);
    }

    private void sendNotification(EventDto eventDto, List<String> usersDevicesIds) {
        HttpHeaders headers = new HttpHeaders();
        Sender<EventDto> eventSender = new Sender<>();

        String authorization = "key={0}";
        String headerAuthorization = MessageFormat.format(authorization,
                "AAAAsimAgCE:APA91bG7upq7kCkpXPeGzUOsvOVhEYCTflrHOxdhrrwVK3LeK1BdD2sQe4_pzasURelxWP6OLrZSMhualXjG_ZtEsdTr84-2gH1JaGoqmtspalw8wrLumcasyyJFf_YhFaok3dp33Gu7");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", headerAuthorization);

        eventSender.setRegistrationIds(usersDevicesIds);
        eventSender.setData(eventDto);

        String Bodycontent = new Gson().toJson(eventSender);

        HttpEntity<String> requestEntity
                = new HttpEntity<>(Bodycontent, headers);

        String serverUrl = "https://fcm.googleapis.com/fcm/send";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(serverUrl, requestEntity, String.class);
    }

}
