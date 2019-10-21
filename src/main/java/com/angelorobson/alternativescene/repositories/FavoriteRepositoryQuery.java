package com.angelorobson.alternativescene.repositories;

import com.angelorobson.alternativescene.entities.Favorite;
import com.angelorobson.alternativescene.entities.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteRepositoryQuery {

    Page<Favorite> findAllPagedByUser(UserApp userApp, Pageable page);
}
