package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.EventDate;
import com.angelorobson.alternativescene.enums.StatusEnum;
import com.angelorobson.alternativescene.repositories.eventdate.EventDateRepository;
import com.angelorobson.alternativescene.services.EventDateService;
import com.angelorobson.alternativescene.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.angelorobson.alternativescene.constants.Constants.TimeZone.TIME_ZONE;

@Service
public class EventDateServiceImpl implements EventDateService {

    private EventDateRepository eventDateRepository;
    private EventService eventService;

    @Autowired
    public EventDateServiceImpl(EventDateRepository eventDateRepository, EventService eventService) {
        this.eventDateRepository = eventDateRepository;
        this.eventService = eventService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0 8 * * *", zone = TIME_ZONE)
    public void checkEventDates() {
        List<Event> events = eventService.findAllByStatus(StatusEnum.APPROVED);
        events.forEach(item -> {
            Long dateRaiment = eventDateRepository.countByDateGreaterThanEqualAndEvent(LocalDate.now(), item);
            if (dateRaiment <= 0) {
                updateStatusEventToExpired(item);
            }
        });
    }

    @Override
    public List<EventDate> save(List<EventDate> eventDate) {
        return eventDateRepository.saveAll(eventDate);
    }

    private void updateStatusEventToExpired(Event item) {
        item.setStatus(StatusEnum.EXPIRED);
        eventService.save(item);
    }
}
