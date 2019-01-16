package com.angelorobson.alternativescene.repositories.event;

import com.angelorobson.alternativescene.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryQuery {
}
