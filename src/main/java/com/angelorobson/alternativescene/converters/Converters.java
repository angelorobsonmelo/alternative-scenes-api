package com.angelorobson.alternativescene.converters;

import com.angelorobson.alternativescene.dtos.*;
import com.angelorobson.alternativescene.entities.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Converters {

    public static EventDto convertEventEntityToDto(Event event) {
        List<EventDateDto> eventDateDtos = event.getEventDates().stream().map(Converters::convertEventDateEntityToDto).collect(toList());
        List<MusicalGenreDto> musicalGenreDtos = event.getMusicalGenres().stream().map(Converters::convertMusicalGenreDtoEntityToDto).collect(toList());

        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setStatus(event.getStatus());
        eventDto.setPhotoUrl(event.getPhotoUrl());
        eventDto.setTitle(event.getTitle());
        eventDto.setRegistrationDate(event.getRegistrationDate());
        eventDto.setDescription(event.getDescription());
        eventDto.setLocality(event.getLocality().getLocality());
        eventDto.setCity(event.getLocality().getCity().getCity());
        eventDto.setState(event.getLocality().getCity().getState().getState());
        eventDto.setUserApp(convertUserAppEntityToDto(event.getUserApp()));
        eventDto.setEventDates(eventDateDtos);
        eventDto.setCategory(event.getCategory().getCategory());
        eventDto.setMusicalGenres(musicalGenreDtos);

        return eventDto;
    }

    public static UserAppDto convertUserAppEntityToDto(UserApp userApp) {
        UserAppDto userAppDto = new UserAppDto();

        userAppDto.setId(userApp.getId());
        userAppDto.setName(userApp.getName());
        userAppDto.setImageUrl(userApp.getImageUrl());
        userAppDto.setEmail(userApp.getEmail());
        userAppDto.setRegistrationDate(userApp.getRegistrationDate());
        return userAppDto;
    }

    private static EventDateDto convertEventDateEntityToDto(EventDate eventDate) {
        EventDateDto eventDateDto = new EventDateDto();

        eventDateDto.setDate(eventDate.getEventDate());
        eventDateDto.setPriceDate(eventDate.getPriceDate().getPrice());
        eventDateDto.setHour(eventDate.getEventHour());

        return eventDateDto;
    }

    private static MusicalGenreDto convertMusicalGenreDtoEntityToDto(MusicalGenre musicalGenre) {
        MusicalGenreDto musicalGenreDto = new MusicalGenreDto();

        musicalGenreDto.setName(musicalGenre.getName());

        return musicalGenreDto;
    }


    public static Event converterDtoParaLancamento(EventSaveDto eventSaveDto) {
        List<MusicalGenre> musicalGenres = convertMusicalGenresIdsToEntity(eventSaveDto.getMusicalGenres());

        UserApp userApp = new UserApp();
        userApp.setId(eventSaveDto.getUserAppId());

        Category category = new Category();
        category.setId(eventSaveDto.getCategoryId());

        State state = new State();
        state.setId(eventSaveDto.getStateId());

        City city = new City();
        city.setId(eventSaveDto.getCityId());

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
        event.setPhotoUrl("a url da foto vai ser pega aqui no back-end");

        List<EventDate> eventDates = convertEventDatesListToEntity(eventSaveDto.getEventDates(), event);

        event.setEventDates(eventDates);

        return event;
    }

    public static List<MusicalGenre> convertMusicalGenresIdsToEntity(List<Long> musicalGenreIds) {
        List<MusicalGenre> musicalGenres = new ArrayList<>();

        musicalGenreIds.forEach(id -> {
            MusicalGenre MusicalGenre = new MusicalGenre();
            MusicalGenre.setId(id);

            musicalGenres.add(MusicalGenre);
        });

        return musicalGenres;
    }

    public static List<EventDate> convertEventDatesListToEntity(List<DateEventSaveDto> eventDateDtos, Event event) {
        List<EventDate> eventDates = new ArrayList<>();

        eventDateDtos.forEach(eventDateDto -> {
            EventDate eventDate = new EventDate();
            PriceDate priceDate = new PriceDate();
            priceDate.setPrice(eventDateDto.getPriceDate());
            eventDate.setEventDate(eventDateDto.getEventDate());
            eventDate.setEventHour(eventDateDto.getEventHour());
            eventDate.setPriceDate(priceDate);
            eventDate.setEvent(event);
            priceDate.setEventDate(eventDate);

            eventDates.add(eventDate);

        });

        return eventDates;
    }
}