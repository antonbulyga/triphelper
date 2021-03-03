package com.bulyha.triphelper.repository;

import com.bulyha.triphelper.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCityName(final String cityName);

}
