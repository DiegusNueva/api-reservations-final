package com.edteam.apireservationsfinal.mapper;

import com.edteam.apireservationsfinal.model.Reservation;
import com.edteam.apireservationsfinal.dto.ReservationDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public interface ReservationsMapper extends Converter<List<Reservation>, List<ReservationDTO>> {

    @Override
    List<ReservationDTO> convert(List<Reservation> source);

}
