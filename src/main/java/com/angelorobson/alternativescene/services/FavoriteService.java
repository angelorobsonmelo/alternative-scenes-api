package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.entities.Favorite;

import java.util.Optional;

public interface FavoriteService {

    Favorite save(Favorite favorite);

    Optional<Favorite> findById(Long id);

    void deleteById(Long id);

    Optional<Favorite> findFavoriteByEventIdAndUserAppId(Long eventId, Long userAppId);
}
