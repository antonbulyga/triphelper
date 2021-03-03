package com.bulyha.triphelper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private Long id;

    private String cityName;

    private String cityInfo;

    @Override
    public String toString() {
        return cityName + ": "
                + cityInfo;
    }

}
