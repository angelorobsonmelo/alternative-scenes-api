package com.angelorobson.alternativescene.controllers;


import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.dtos.EventSaveDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.response.Response;
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

import static com.angelorobson.alternativescene.converters.Converters.*;
import static org.springframework.data.domain.Sort.Direction.valueOf;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<Response<Page<EventDto>>> findAllByFilter(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestParam(value = "perPage", defaultValue = "25") String perPage,
            @RequestBody EventFilter eventFilter) {
        eventFilter.setStatus(true);
        Response<Page<EventDto>> response = new Response<>();

        Pageable pageRequest = new PageRequest(pag, Integer.valueOf(perPage), valueOf(dir), ord);
        Page<EventDto> eventsReturned = this.eventService.findAllByFilter(eventFilter, pageRequest);

        response.setData(eventsReturned);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<EventDto>> save(@RequestBody EventSaveDto eventSaveDto,
                                                   BindingResult result) {
        Response<EventDto> response = new Response<>();
        Event event = converterEventSaveDtoToEntity(eventSaveDto);

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        event = this.eventService.save(event);
        response.setData(convertEventEntityToDto(event));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<EventDto>> findOne(@PathVariable("id") Long id) {
        Response<EventDto> response = new Response<>();

        Optional<EventDto> eventDtoReturned = this.eventService.findOne(id);
        if (eventDtoReturned.isPresent()) {
            response.setData(eventDtoReturned.get());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/getBy/{id}/{userId}/{status}")
    public ResponseEntity<Response<EventDto>> findOne(@PathVariable("id") Long id,
                                                      @PathVariable("userId") Long userId,
                                                      @PathVariable("status") Boolean status) {
        Response<EventDto> response = new Response<>();

        Optional<EventDto> eventDtoReturned = this.eventService.findByIdAndUserAppIdAndStatus(id, userId, status);
        if (eventDtoReturned.isPresent()) {
            response.setData(eventDtoReturned.get());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
        Response<String> response = new Response<>();
        Optional<EventDto> eventDtoReturned = this.eventService.findOne(id);

        if (!eventDtoReturned.isPresent()) {
            response.getErrors().add("Error removing. Record not found for id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.eventService.remove(id);
        return ResponseEntity.ok(new Response<>());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EventDto>> update(@PathVariable("id") Long id,
                                                       @RequestBody EventSaveDto eventSaveDto, BindingResult result) {
        Response<EventDto> response = new Response<>();
        eventSaveDto.setId(Optional.of(id));

        Event contact = convertEventSaveDtoToEntity(eventSaveDto, result);

//        Contact contactReturned = this.contactService.edit(contact);
//
//        ContactDto contactDto = convertContactEntityToDto(contactReturned);
        EventDto eventDto = new EventDto();
        response.setData(eventDto);
        return ResponseEntity.ok(response);
    }

    private Event convertEventSaveDtoToEntity(EventSaveDto eventSaveDto, BindingResult result) {
//        Contact contact = getContactEntityFromDto(contactSaveDto);
        if (eventSaveDto.getId().isPresent()) {
            Optional<Event> eventReturned = this.eventService.findEventBy(eventSaveDto.getId().get());
            if (eventReturned.isPresent()) {
                Event event = eventReturned.get();


                return event;
            } else {
                result.addError(new ObjectError("user", "User not found."));
            }
        }

//        if (contactSaveDto.getId().isPresent()) {
//            Optional<Contact> contactDataBaseReturned = this.contactService.findById(contactSaveDto.getId().get());
//            if (contactDataBaseReturned.isPresent()) {
//                Contact contactEntityFromDto = getContactEntityFromDto(contactSaveDto);
//
//                contactDataBaseReturned.get().setCategory(contactEntityFromDto.getCategory());
//                contactDataBaseReturned.get().setUserNameInstagram(contactEntityFromDto.getUserNameInstagram());
//                contactDataBaseReturned.get().setFunctions(contactEntityFromDto.getFunctions());
//                contactDataBaseReturned.get().setGender(contactEntityFromDto.getGender());
//
//                return  contactDataBaseReturned.get();
//            } else {
//                result.addError(new ObjectError("user", "User not found."));
//            }
//        }

        return new Event();
    }

}
