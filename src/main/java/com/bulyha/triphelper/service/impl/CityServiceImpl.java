package com.bulyha.triphelper.service.impl;

import com.bulyha.triphelper.model.City;
import com.bulyha.triphelper.repository.CityRepository;
import com.bulyha.triphelper.service.CityService;
import com.bulyha.triphelper.exception.CityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    public CityServiceImpl(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Optional<City> findById(final Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public Optional<City> findByCityName(final String cityName) {
        return cityRepository.findByCityNameIgnoreCaseContaining(cityName);
    }


    @Override
    public City save(final City city) {
        return cityRepository.save(city);
    }

    @Override
    public void delete(final City city) {
        cityRepository.delete(city);
    }

    @Override
    public City update(final City updatedCity) throws CityNotFoundException {
        City city = cityRepository.findById(updatedCity.getId()).orElseThrow(CityNotFoundException::new);
        city.setCityName(updatedCity.getCityName());
        city.setCityInfo(updatedCity.getCityInfo());
        return city;
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
