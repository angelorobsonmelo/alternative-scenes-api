package com.angelorobson.alternativescene.converters;

import com.angelorobson.alternativescene.dtos.*;
import com.angelorobson.alternativescene.entities.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class Converters {

    public static EventDto convertEventEntityToDto(Event event) {
        List<EventDateDto> eventDateDtos = getEventDatesDto(event);
        List<MusicalGenreDto> musicalGenreDtos = getMusicalGenresDto(event);

        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setStatus(event.getStatus());
        eventDto.setImageUrl(event.getImageUrl());
        eventDto.setImageThumbUrl(event.getImageThumbUrl());
        eventDto.setRegistrationDate(event.getRegistrationDate());
        eventDto.setLocality(convertLocalityDtoToEntity(event.getLocality()));
        eventDto.setUserApp(convertUserAppEntityToDto(event.getUserApp()));
        eventDto.setEventDates(eventDateDtos);
        eventDto.setCategory(convertCategoryDtoToEntity(event.getCategory()));
        eventDto.setMusicalGenres(musicalGenreDtos);
        eventDto.setEventDate(getFormatedDates(eventDateDtos));

        String eventLocationFormat = String.format("%s - %s, %s",
                event.getLocality().getName(),
                event.getLocality().getCity().getName(),
                event.getLocality().getCity().getState().getUf());
        eventDto.setEventLocation(eventLocationFormat);

        return eventDto;
    }

    private static List<MusicalGenreDto> getMusicalGenresDto(Event event) {
        return event.getMusicalGenres().stream().map(Converters::convertMusicalGenreDtoEntityToDto).collect(toList());
    }

    public static EventDto convertEventEntityWithFavoriteToDto(Event event, List<Favorite> favorites) {
        List<EventDateDto> eventDateDtos = getEventDatesDto(event);
        List<MusicalGenreDto> musicalGenreDtos = getMusicalGenresDto(event);

        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setStatus(event.getStatus());
        eventDto.setImageUrl(event.getImageUrl());
        eventDto.setImageThumbUrl(event.getImageThumbUrl());
        eventDto.setRegistrationDate(event.getRegistrationDate());
        eventDto.setLocality(convertLocalityDtoToEntity(event.getLocality()));
        eventDto.setUserApp(convertUserAppEntityToDto(event.getUserApp()));
        eventDto.setEventDates(eventDateDtos);
        eventDto.setCategory(convertCategoryDtoToEntity(event.getCategory()));
        eventDto.setMusicalGenres(musicalGenreDtos);
        eventDto.setEventDate(getFormatedDates(eventDateDtos));

        String eventLocationFormat = String.format("%s - %s, %s",
                event.getLocality().getName(),
                event.getLocality().getCity().getName(),
                event.getLocality().getCity().getState().getUf());
        eventDto.setEventLocation(eventLocationFormat);

        eventDto.setFavorite(false);
        favorites.forEach(favorite -> {
            if (favorite.getEvent().getId().equals(event.getId())) {
                eventDto.setFavorite(true);
            }
        });

        return eventDto;
    }


    public static EventDto convertEventEntityWithFavoriteTrueToDto(Event event) {
        List<EventDateDto> eventDateDtos = getEventDatesDto(event);
        List<MusicalGenreDto> musicalGenreDtos = getMusicalGenresDto(event);

        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setStatus(event.getStatus());
        eventDto.setImageUrl(event.getImageUrl());
        eventDto.setImageThumbUrl(event.getImageThumbUrl());
        eventDto.setRegistrationDate(event.getRegistrationDate());
        eventDto.setLocality(convertLocalityDtoToEntity(event.getLocality()));
        eventDto.setUserApp(convertUserAppEntityToDto(event.getUserApp()));
        eventDto.setEventDates(eventDateDtos);
        eventDto.setCategory(convertCategoryDtoToEntity(event.getCategory()));
        eventDto.setMusicalGenres(musicalGenreDtos);
        eventDto.setEventDate(getFormatedDates(eventDateDtos));
        eventDto.setFavorite(true);

        String eventLocationFormat = String.format("%s - %s, %s",
                event.getLocality().getName(),
                event.getLocality().getCity().getName(),
                event.getLocality().getCity().getState().getUf());
        eventDto.setEventLocation(eventLocationFormat);

        return eventDto;
    }

    private static List<EventDateDto> getEventDatesDto(Event event) {
        return event.getEventDates().stream().map(Converters::convertEventDateEntityToDto).collect(toList());
    }

    private static String getFormatedDates(List<EventDateDto> eventDateDtos) {
        List<String> datesFormated = new ArrayList<>();

        eventDateDtos.forEach(
                it -> datesFormated.add(it.getEventDateAndHourToString())
        );

        return String.join(" - ", datesFormated);
    }

    private static LocalityDto convertLocalityDtoToEntity(Locality locality) {
        LocalityDto localityDto = new LocalityDto();
        localityDto.setId(locality.getId());
        localityDto.setName(locality.getName());
        localityDto.setAddress(locality.getAddress());
        localityDto.setLatitude(locality.getLatitude());
        localityDto.setLongitude(locality.getLongitude());
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
        userAppDto.setGoogleAccountId(userApp.getGoogleAccountId());
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
        eventDateDto.setPrice(eventDate.getPriceDate().getPrice());
        eventDateDto.setHour(eventDate.getEventHour());
        eventDateDto.setEventDateAndHourToString();

        return eventDateDto;
    }

    private static MusicalGenreDto convertMusicalGenreDtoEntityToDto(MusicalGenre musicalGenre) {
        MusicalGenreDto musicalGenreDto = new MusicalGenreDto();

        musicalGenreDto.setId(musicalGenre.getId());
        musicalGenreDto.setName(musicalGenre.getName());

        return musicalGenreDto;
    }


    public static Event converterEventSaveDtoToEntity(EventSaveDto eventSaveDto, City city) {
        List<MusicalGenre> musicalGenres = singletonList(new MusicalGenre(1L));

        UserApp userApp = new UserApp();
        userApp.setId(eventSaveDto.getUserAppId());

        Category category = new Category();
        category.setId(1L);
        if (eventSaveDto.getEventDates().size() > 1) {
            category.setId(2L);
        }

        State state = new State();
        state.setId(city.getState().getId());

        Locality locality = new Locality();
        locality.setCity(city);
        locality.getCity().setState(state);
        locality.setName(eventSaveDto.getLocality());
        locality.setAddress(eventSaveDto.getAddress());
        locality.setLatitude(eventSaveDto.getLatitude());
        locality.setLongitude(eventSaveDto.getLongitude());

        Event event = new Event();
        event.setLocality(locality);
        event.setCategory(category);
        event.setMusicalGenres(musicalGenres);
        event.setUserApp(userApp);
        event.setImageUrl(eventSaveDto.getImageUrl());
        event.setImageThumbUrl(eventSaveDto.getImageThumbUrl());

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

    public static List<EventDate> convertEventDatesListToEntity(List<DateEventSaveDto> eventDateDtos) {
        List<EventDate> eventDates = new ArrayList<>();

        eventDateDtos.forEach(eventDateDto -> {
            EventDate eventDate = new EventDate();
            PriceDate priceDate = new PriceDate();
            priceDate.setPrice(eventDateDto.getPrice());
            eventDate.setDate(eventDateDto.getDate());
            eventDate.setEventHour(eventDateDto.getHour());
            eventDate.setPriceDate(priceDate);
            priceDate.setEventDate(eventDate);

            eventDates.add(eventDate);

        });

        return eventDates;
    }
}
