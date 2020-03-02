package com.angelorobson.alternativescene.repositories.eventdate;

import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.EventDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface EventDateRepository extends JpaRepository<EventDate, Long> {

    Long countByDateGreaterThanEqualAndEvent(LocalDate date, Event event);
}
