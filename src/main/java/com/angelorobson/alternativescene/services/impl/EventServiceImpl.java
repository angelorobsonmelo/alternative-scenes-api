package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.converters.Converters;
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

        return eventPage.map(Converters::convertEventEntityToDto);
    }

}
