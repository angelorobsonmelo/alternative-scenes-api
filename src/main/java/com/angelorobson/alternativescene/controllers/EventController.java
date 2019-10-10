package com.angelorobson.alternativescene.controllers;


import com.angelorobson.alternativescene.converters.Converters;
import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.dtos.EventSaveDto;
import com.angelorobson.alternativescene.entities.City;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.Locality;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.CityService;
import com.angelorobson.alternativescene.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @Autowired
    public EventController(EventService eventService, CityService cityService) {
        this.eventService = eventService;
        this.cityService = cityService;
    }

    @PostMapping(value = "/findAll")
    public ResponseEntity<Response<Page<EventDto>>> findAll(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestParam(value = "perPage", defaultValue = "25") String perPage) {
        EventFilter eventFilter = new EventFilter();
        eventFilter.setStatus(true);
        Response<Page<EventDto>> response = new Response<>();

        PageRequest pageRequest = PageRequest.of(pag, Integer.parseInt(perPage), valueOf(dir), ord);

        Page<EventDto> eventsReturned = this.eventService.findAllByFilter(eventFilter, pageRequest);

        response.setData(eventsReturned);
        return ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Response<EventDto>> save(@RequestBody EventSaveDto eventSaveDto,
                                                   BindingResult result) {
        Response<EventDto> response = new Response<>();
        Optional<City> cityReturned = cityService.findByName(eventSaveDto.getCityName());

        if (cityReturned.isPresent()) {
            Event event = converterEventSaveDtoToEntity(eventSaveDto, cityReturned.get());
            event = this.eventService.save(event);
            response.setData(convertEventEntityToDto(event));
            return ok(response);
        }


        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return badRequest().body(response);
        }

        return notFound().build();
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

}
