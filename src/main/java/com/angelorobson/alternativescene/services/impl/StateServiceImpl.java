package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.State;
import com.angelorobson.alternativescene.repositories.StateRepository;
import com.angelorobson.alternativescene.services.StateSerivice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateSerivice {

    private static final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

    @Autowired
    private StateRepository stateRepository;

    @Override
    public List<State> findAll() {
        log.info("Get all states");
        return stateRepository.findAll();
    }
}
