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
        eventDto.setLocality(convertLocalityDtoToEntity(event.getLocality()));
        eventDto.setUserApp(convertUserAppEntityToDto(event.getUserApp()));
        eventDto.setEventDates(eventDateDtos);
        eventDto.setCategory(convertCategoryDtoToEntity(event.getCategory()));
        eventDto.setMusicalGenres(musicalGenreDtos);

        return eventDto;
    }

    private static LocalityDto convertLocalityDtoToEntity(Locality locality) {
        LocalityDto localityDto = new LocalityDto();
        localityDto.setId(locality.getId());
        localityDto.setName(locality.getName());
        localityDto.setCity(convertCityDtoToEntity(locality.getCity()));

        return localityDto;
    }

    private static CityDto convertCityDtoToEntity(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        cityDto.setState(convertStateDtoToEntity(city.getState()));

        return cityDto;
    }

    private static StateDto convertStateDtoToEntity(State state) {
        StateDto stateDto = new StateDto();
        stateDto.setId(state.getId());
        stateDto.setName(state.getName());
        stateDto.setUf(state.getUf());


        return stateDto;
    }

    private static CategoryDto convertCategoryDtoToEntity(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
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

        eventDateDto.setId(eventDate.getId());
        eventDateDto.setDate(eventDate.getDate());
        eventDateDto.setPriceDate(eventDate.getPriceDate().getPrice());
        eventDateDto.setHour(eventDate.getEventHour());

        return eventDateDto;
    }

    private static MusicalGenreDto convertMusicalGenreDtoEntityToDto(MusicalGenre musicalGenre) {
        MusicalGenreDto musicalGenreDto = new MusicalGenreDto();

        musicalGenreDto.setId(musicalGenre.getId());
        musicalGenreDto.setName(musicalGenre.getName());

        return musicalGenreDto;
    }


    public static Event converterEventSaveDtoToEntity(EventSaveDto eventSaveDto) {
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
        locality.setName(eventSaveDto.getLocality());

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
            priceDate.setPrice(eventDateDto.getPrice());
            eventDate.setDate(eventDateDto.getDate());
            eventDate.setEventHour(eventDateDto.getHour());
            eventDate.setPriceDate(priceDate);
            eventDate.setEvent(event);
            priceDate.setEventDate(eventDate);

            eventDates.add(eventDate);

        });

        return eventDates;
    }
}
