package com.angelorobson.alternativescene.controllers;


import com.angelorobson.alternativescene.dtos.EventDto;
import com.angelorobson.alternativescene.dtos.FavoriteSaveDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.Favorite;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.enums.StatusEnum;
import com.angelorobson.alternativescene.repositories.event.filter.EventFilter;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.valueOf;
import static org.springframework.http.ResponseEntity.ok;

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


    @PostMapping(value = "/findAllByUserId")
    public ResponseEntity<Response<Page<EventDto>>> findAllByUserId(
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "ASC") String dir,
            @RequestParam(value = "perPage", defaultValue = "25") String perPage) {
        Response<Page<EventDto>> response = new Response<>();

        PageRequest pageRequest = PageRequest.of(pag, Integer.parseInt(perPage), valueOf(dir), ord);

        Page<EventDto> eventsReturned = this.favoriteService.findAllByUserWithPaged(userId, pageRequest);
        response.setData(eventsReturned);
        return ok(response);
    }


}
