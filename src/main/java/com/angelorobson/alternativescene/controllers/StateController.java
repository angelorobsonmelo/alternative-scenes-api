package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.entities.State;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.StateSerivice;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/states")
@CrossOrigin(origins = "http://localhost:8080")
public class StateController {

    private static final Logger log = getLogger(StateController.class);

    @Autowired
    private StateSerivice stateSerivice;

    /**
     * Return list of states
     *
     * @return ResponseEntity<Response<List<State>>>
     */
    @GetMapping
    public ResponseEntity<Response<List<State>>> findAll() {
        log.info("Searching for all states");
        Response<List<State>> response = new Response<>();

        List<State> states = stateSerivice.findAll();

        response.setData(states);
        return ResponseEntity.ok(response);

    }

}
