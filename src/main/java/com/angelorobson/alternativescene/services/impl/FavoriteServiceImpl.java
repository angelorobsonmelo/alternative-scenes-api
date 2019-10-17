package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.Favorite;
import com.angelorobson.alternativescene.repositories.FavoriteRepository;
import com.angelorobson.alternativescene.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public Optional<Favorite> findById(Long id) {
        return favoriteRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        favoriteRepository.deleteById(id);
    }

    @Override
    public Optional<Favorite> findFavoriteByEventIdAndUserAppId(Long eventId, Long userAppId) {
        Favorite favorite = favoriteRepository.findFavoriteByEventIdAndUserAppId(eventId, userAppId);
        return Optional.ofNullable(favorite);
    }
}
