package com.angelorobson.alternativescene.controllers;


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
    private EventDateService eventDateService;
    private PriceDateService priceDateService;

    @Autowired
    public EventController(EventService eventService, EventDateService eventDateService, PriceDateService priceDateService) {
        this.eventService = eventService;
        this.eventDateService = eventDateService;
        this.priceDateService = priceDateService;
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
                                                   BindingResult result) throws ParseException {
        Response<EventDto> response = new Response<>();
        Event event = this.converterDtoParaLancamento(eventSaveDto, result);

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        event = this.eventService.save(event);

        List<EventDate> eventDates = convertEventDatesListToEntity(eventSaveDto.getEventDates(), event);
//        eventDates = eventDateService.save(eventDates);

//        eventDates.forEach(item -> {
//            PriceDate priceDate = new PriceDate();
//            priceDate.setEventDate(item);
//            this.priceDateService.save(priceDate);
//        });

        response.setData(convertEventEntityToDto(event));
        return ResponseEntity.ok(response);
    }

    private Event converterDtoParaLancamento(EventSaveDto eventSaveDto, BindingResult result) throws ParseException {
        List<MusicalGenre> musicalGenres = convertMusicalGenresIdsToEntity(eventSaveDto.getMusicalGenres());

        UserApp userApp = new UserApp();
        userApp.setId(eventSaveDto.getUserAppId());

        Category category = new Category();
        category.setId(eventSaveDto.getCategoryId());

        State state = new State();
        state.setId(eventSaveDto.getStateId());

//        locality.setCity(city);
//        city.setState(state);

        City city = new City();
        city.setId(eventSaveDto.getCityId());
//        city.setState(state);

        Locality locality = new Locality();
        locality.setCity(city);
        locality.getCity().setState(state);
        locality.setLocality(eventSaveDto.getLocality());

        Event event = new Event();
        event.setTitle(eventSaveDto.getTitle());
        event.setDescription(eventSaveDto.getDescription());
        event.setLocality(locality);
        event.setStatus(false);
        event.setCategory(category);
        event.setLocality(locality);
        event.setMusicalGenres(musicalGenres);
        event.setUserApp(userApp);
        event.setPhotoUrl("url da foto do evento");

        return event;
    }

    private List<MusicalGenre> convertMusicalGenresIdsToEntity(List<Long> musicalGenreIds) {
        List<MusicalGenre> musicalGenres = new ArrayList<>();

        musicalGenreIds.forEach(id -> {
            MusicalGenre MusicalGenre = new MusicalGenre();
            MusicalGenre.setId(id);

            musicalGenres.add(MusicalGenre);
        });

        return musicalGenres;
    }

    private List<EventDate> convertEventDatesListToEntity(List<DateEventSaveDto> eventDateDtos, Event event) {
        List<EventDate> eventDates = new ArrayList<>();

        eventDateDtos.forEach(eventDateDto -> {
            EventDate eventDate = new EventDate();
            PriceDate priceDate = new PriceDate();
            priceDate.setPrice(eventDateDto.getPriceDate());
            eventDate.setEventDate(eventDateDto.getEventDate());
            eventDate.setEventHour(eventDateDto.getEventHour());
            eventDate.setPriceDate(priceDate);
            eventDate.setEvent(event);

            eventDates.add(eventDate);

        });

        return eventDates;
    }


}
