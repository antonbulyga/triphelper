package com.bulyha.triphelper.utils.mapper;

import com.bulyha.triphelper.model.City;
import com.bulyha.triphelper.dto.CityDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CityMapper {

    private ModelMapper mapper;

    public CityMapper(final ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    public City toEntity(final CityDto cityDto) {
        return Objects.isNull(cityDto) ? null : mapper.map(cityDto, City.class);
    }

    public CityDto toDto(final City city) {
        return Objects.isNull(city) ? null : mapper.map(city, CityDto.class);
    }
}

