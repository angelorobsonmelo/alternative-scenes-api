package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.entities.City;

import java.util.List;

public interface CityService {

    /**
     * Search all cities by state
     *
     * @return City
     */
    List<City> findAllByState(Long id);

    City findCityById(Long id);
}
