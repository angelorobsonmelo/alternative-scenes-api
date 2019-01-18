package com.angelorobson.alternativescene.converters;

import com.angelorobson.alternativescene.dtos.EventDateDto;
import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.dtos.MusicalGenreDto;
import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.EventDate;
import com.angelorobson.alternativescene.entities.MusicalGenre;
import com.angelorobson.alternativescene.entities.UserApp;

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
}
