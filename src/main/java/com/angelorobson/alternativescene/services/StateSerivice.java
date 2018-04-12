package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.entities.State;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StateSerivice {

    /**
     * Search all states
     *
     * @return State
     */
    List<State> findAll();
}
