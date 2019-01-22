package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EventService {

    Page<EventDto> findAllByFilter(EventFilter eventFilter, Pageable pageable);

    Event save(Event event);

    Optional<EventDto> findOne(Long id);

    Optional<EventDto> findByIdAndUserAppIdAndStatus(Long id, Long userId, Boolean status);

    void remove(Long id);
}
