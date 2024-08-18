package com.edteam.apireservationsfinal.mapper;

import com.edteam.apireservationsfinal.model.Reservation;
import com.edteam.apireservationsfinal.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ReservationMapper extends Converter<Reservation, ReservationDTO> {

    @Override
    ReservationDTO convert(Reservation source);

}
