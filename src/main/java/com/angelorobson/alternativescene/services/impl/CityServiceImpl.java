package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.City;
import com.angelorobson.alternativescene.repositories.CityRepository;
import com.angelorobson.alternativescene.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> findAllByState(Long id) {
        log.info("Searching all cyties by state");
        return cityRepository.findAllByStateId(id);
    }
}
