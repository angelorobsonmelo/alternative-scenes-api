package com.angelorobson.alternativescene.repositories.event;

import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryQuery {

    Event findByIdAndUserAppIdAndStatus(Long id, Long userId, Boolean status);

    List<Event> findAllByStatus(StatusEnum status);
}
