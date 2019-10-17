package com.angelorobson.alternativescene.controllers;


import com.angelorobson.alternativescene.dtos.FavoriteSaveDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.Favorite;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/favorites")
public class FavoriteController {

    private FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<Response<Favorite>> save(@RequestBody FavoriteSaveDto favoriteSaveDto) {
        Response<Favorite> response = new Response<>();
        Optional<Favorite> favoriteOptionalReturned = favoriteService.findFavoriteByEventIdAndUserAppId(favoriteSaveDto.getEventId(), favoriteSaveDto.getUserAppId());

        if (favoriteOptionalReturned.isPresent()) {
            favoriteService.deleteById(favoriteOptionalReturned.get().getId());
            response.setData(new Favorite());
            return ResponseEntity.ok(response);
        }

        Favorite favorite = convertDtoToEntity(favoriteSaveDto);
        Favorite favoriteReturned = favoriteService.save(favorite);
        response.setData(favoriteReturned);
        return ResponseEntity.ok(response);
    }

    private Favorite convertDtoToEntity(@RequestBody FavoriteSaveDto favoriteSaveDto) {
        Favorite favorite = new Favorite();
        Event event = new Event();
        event.setId(favoriteSaveDto.getEventId());

        UserApp userApp = new UserApp();
        userApp.setId(favoriteSaveDto.getUserAppId());
        favorite.setEvent(event);
        favorite.setUserApp(userApp);

        return favorite;
    }


}
