package com.angelorobson.alternativescene.repositories;

import com.angelorobson.alternativescene.entities.Favorite;
import com.angelorobson.alternativescene.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite findFavoriteByEventIdAndUserAppId(Long eventId, Long userAppId);
    List<Favorite> findAllByUserAppIn(UserApp userApp);
}
