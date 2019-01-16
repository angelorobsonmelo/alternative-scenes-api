package com.angelorobson.alternativescene.repositories.event;

import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRepositoryQuery {

    Page<Event> findAllByFilter(EventFilter eventFilter, Pageable page);

}
