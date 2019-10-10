package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.entities.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    /**
     * Search all cities by state
     *
     * @return City
     */
    List<City> findAllByState(Long id);

    City findCityById(Long id);

    Optional<City> findByName(String name);
}
