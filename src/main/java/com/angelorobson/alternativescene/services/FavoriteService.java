package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.entities.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FavoriteService {

    Favorite save(Favorite favorite);

    Optional<Favorite> findById(Long id);

    void deleteById(Long id);

    Optional<Favorite> findFavoriteByEventIdAndUserAppId(Long eventId, Long userAppId);

    Page<EventDto> findAllByUserWithPaged(Long UserAppId, Pageable pageable);

}
