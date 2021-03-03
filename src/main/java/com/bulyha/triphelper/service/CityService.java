package com.bulyha.triphelper.service;

import com.bulyha.triphelper.model.City;
import com.bulyha.triphelper.exception.CityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CityService {

    Optional<City> findById(Long id);

    Optional<City> findByCityName(String cityName);

    City save(City city);

    void delete(City city);

    City update(City city) throws CityNotFoundException;

    List<City> findAll();

}
