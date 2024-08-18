package com.edteam.apireservationsfinal.mapper;

import com.edteam.apireservationsfinal.model.Reservation;
import com.edteam.apireservationsfinal.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ReservationDTOMapper extends Converter<ReservationDTO, Reservation> {

    @Override
    Reservation convert(ReservationDTO source);

}
