package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.entities.City;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.CityService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/cities")
@CrossOrigin(origins = "http://localhost:8080")
public class CityController {

    private static final Logger log = getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    /**
     * Return list of states
     *
     * @return ResponseEntity<Response<List<State>>>
     */
    @GetMapping("/state/{id}")
    public ResponseEntity<Response<List<City>>> findAllByState(@PathVariable Long id) {
        log.info("Searching for all cities by state: {} ", id);
        Response<List<City>> response = new Response<>();

        List<City> states = cityService.findAllByState(id);

        response.setData(states);
        return ResponseEntity.ok(response);

    }

}
