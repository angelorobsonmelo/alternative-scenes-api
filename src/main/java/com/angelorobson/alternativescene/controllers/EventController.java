package com.angelorobson.alternativescene.controllers;


import com.angelorobson.alternativescene.converters.Converters;
import com.angelorobson.alternativescene.dtos.DateEventSaveDto;
import com.angelorobson.alternativescene.dtos.EventDateDto;
import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.dtos.EventSaveDto;
import com.angelorobson.alternativescene.entities.*;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.angelorobson.alternativescene.converters.Converters.convertEventEntityToDto;
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
        Event event = Converters.converterDtoParaLancamento(eventSaveDto);

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        event = this.eventService.save(event);

        response.setData(convertEventEntityToDto(event));
        return ResponseEntity.ok(response);
    }

}
