package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.converters.Converters;
import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.Favorite;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.enums.StatusEnum;
import com.angelorobson.alternativescene.repositories.FavoriteRepository;
import com.angelorobson.alternativescene.repositories.event.EventRepository;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private FavoriteRepository favoriteRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, FavoriteRepository favoriteRepository) {
        this.eventRepository = eventRepository;
        this.favoriteRepository = favoriteRepository;
    }


    @Override
    public Page<EventDto> findAllByFilter(EventFilter eventFilter, Pageable pageable) {
        Page<Event> eventPage = eventRepository.findAllByFilter(eventFilter, pageable);
        return eventPage.map(Converters::convertEventEntityToDto);
    }

    @Override
    public Page<EventDto> findAllByUser(EventFilter eventFilter, Pageable pageable, Long userAppId) {
        Page<Event> eventPage = eventRepository.findAllByFilter(eventFilter, pageable);
        UserApp userApp = new UserApp();
        userApp.setId(userAppId);
        List<Favorite> favorites = favoriteRepository.findAllByUserAppIn(userApp);

        return eventPage.map(event -> Converters.convertEventEntityWithFavoriteToDto(event, favorites));
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Optional<EventDto> findOne(Long id) {
        EventDto eventDto = new EventDto();
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            eventDto = Converters.convertEventEntityToDto(event.get());
        }

        return Optional.ofNullable(eventDto);
    }

    @Override
    public Optional<Event> findEventBy(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Optional<EventDto> findByIdAndUserAppIdAndStatus(Long id, Long userId, Boolean status) {
        Event event = eventRepository.findByIdAndUserAppIdAndStatus(id, userId, status);
        EventDto eventDto = Converters.convertEventEntityToDto(event);
        return Optional.of(eventDto);
    }

    @Override
    public void remove(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> findAllByStatus(StatusEnum status) {
        return eventRepository.findAllByStatus(status);
    }

}
