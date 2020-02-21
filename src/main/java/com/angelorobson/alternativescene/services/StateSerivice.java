package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.entities.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface StateSerivice {

    /**
     * Search all states
     *
     * @return State
     */
    List<State> findAll();

    Optional<State> findOne(Long id);
}
