package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.repositories.event.EventRepository;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public Page<Event> findAllByFilter(EventFilter eventFilter, Pageable pageable) {
        return eventRepository.findAllByFilter(eventFilter, pageable);
    }
}
