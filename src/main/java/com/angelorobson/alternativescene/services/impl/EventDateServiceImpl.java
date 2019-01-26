package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.EventDate;
import com.angelorobson.alternativescene.repositories.eventdate.EventDateRepository;
import com.angelorobson.alternativescene.services.EventDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventDateServiceImpl implements EventDateService {

    private EventDateRepository eventDateRepository;

    @Autowired
    public EventDateServiceImpl(EventDateRepository eventDateRepository) {
        this.eventDateRepository = eventDateRepository;
    }

    @Override
    public List<EventDate> save(List<EventDate> eventDate) {
        return eventDateRepository.saveAll(eventDate);
    }
}
