package com.edteam.apireservationsfinal.util;

import com.edteam.apireservationsfinal.dto.*;
import com.edteam.apireservationsfinal.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReservationUtil {

    static public Reservation getReservation(Long id, String origin, String destination) {

        Passenger passenger = new Passenger();
        passenger.setFirstName("Andres");
        passenger.setLastName("Sacco");
        passenger.setId(1L);
        passenger.setDocumentType("DNI");
        passenger.setDocumentNumber("12345678");
        passenger.setBirthday(LocalDate.of(1985, 1, 1));

        Price price = new Price();
        price.setBasePrice(BigDecimal.ONE);
        price.setTotalTax(BigDecimal.ZERO);
        price.setTotalPrice(BigDecimal.ONE);

        Segment segment = new Segment();
        segment.setArrival("2025-01-01");
        segment.setDeparture("2024-12-31");
        segment.setOrigin(origin);
        segment.setDestination(destination);
        segment.setCarrier("AA");
        segment.setId(1L);

        Itinerary itinerary = new Itinerary();
        itinerary.setId(1L);
        itinerary.setPrice(price);
        itinerary.setSegment(List.of(segment));

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setPassengers(List.of(passenger));
        reservation.setItinerary(itinerary);

        return reservation;

    }

    static public ReservationDTO getReservationDTO(Long id, String origin, String destination) {

        PassengerDTO passenger = new PassengerDTO();
        passenger.setFirstName("Andres");
        passenger.setLastName("Sacco");
        passenger.setDocumentType("DNI");
        passenger.setDocumentNumber("12345678");
        passenger.setBirthday(LocalDate.of(1985, 1, 1));

        PriceDTO price = new PriceDTO();
        price.setBasePrice(BigDecimal.ONE);
        price.setTotalTax(BigDecimal.ZERO);
        price.setTotalPrice(BigDecimal.ONE);

        SegmentDTO segment = new SegmentDTO();
        segment.setArrival("2025-01-01");
        segment.setDeparture("2024-12-31");
        segment.setOrigin("EZE");
        segment.setDestination("MIA");
        segment.setCarrier("AA");

        ItineraryDTO itinerary = new ItineraryDTO();
        itinerary.setPrice(price);
        itinerary.setSegment(List.of(segment));

        ReservationDTO reservation = new ReservationDTO();
        reservation.setId(1L);
        reservation.setPassengers(List.of(passenger));
        reservation.setItinerary(itinerary);

        return reservation;

    }
}
