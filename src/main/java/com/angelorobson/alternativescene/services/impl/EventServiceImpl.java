package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.dtos.EventDateDto;
import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.dtos.MusicalGenreDto;
import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.EventDate;
import com.angelorobson.alternativescene.entities.MusicalGenre;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.repositories.event.EventRepository;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public Page<EventDto> findAllByFilter(EventFilter eventFilter, Pageable pageable) {
        Page<Event> eventPage = eventRepository.findAllByFilter(eventFilter, pageable);

        return eventPage.map(this::convertEventEntityToDto);
    }


    private EventDto convertEventEntityToDto(Event event) {
        List<EventDateDto> eventDateDtos = event.getEventDates().stream().map(this::convertEventDateEntityToDto).collect(Collectors.toList());
        List<MusicalGenreDto> musicalGenreDtos = event.getMusicalGenres().stream().map(this::convertMusicalGenreDtoEntityToDto).collect(Collectors.toList());

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

    private UserAppDto convertUserAppEntityToDto(UserApp userApp) {
        UserAppDto userAppDto = new UserAppDto();

        userAppDto.setId(userApp.getId());
        userAppDto.setName(userApp.getName());
        userAppDto.setImageUrl(userApp.getImageUrl());
        userAppDto.setEmail(userApp.getEmail());
        userAppDto.setRegistrationDate(userApp.getRegistrationDate());
        return userAppDto;
    }

    private EventDateDto convertEventDateEntityToDto(EventDate eventDate) {
        EventDateDto eventDateDto = new EventDateDto();

        eventDateDto.setDate(eventDate.getEventDate());
        eventDateDto.setPriceDate(eventDate.getPriceDate().getPrice());
        eventDateDto.setHour(eventDate.getEventHour());

        return eventDateDto;
    }

    private MusicalGenreDto convertMusicalGenreDtoEntityToDto(MusicalGenre musicalGenre) {
        MusicalGenreDto musicalGenreDto = new MusicalGenreDto();

        musicalGenreDto.setName(musicalGenre.getName());

        return musicalGenreDto;
    }


}
