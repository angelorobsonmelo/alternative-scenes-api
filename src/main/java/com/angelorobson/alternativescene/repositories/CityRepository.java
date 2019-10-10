package com.angelorobson.alternativescene.repositories;

import com.angelorobson.alternativescene.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByStateId(Long id);

    City findByName(String name);
}
