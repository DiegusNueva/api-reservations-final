package com.edteam.apireservationsfinal.controller;

import com.edteam.apireservationsfinal.dto.ReservationDTO;
import com.edteam.apireservationsfinal.enums.APIError;
import com.edteam.apireservationsfinal.exception.CustomException;
import com.edteam.apireservationsfinal.service.ReservationService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/reservation")
public class ReservationController implements ReservationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService service;

    @Autowired
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        LOGGER.info("Obtain all the reservations");
        List<ReservationDTO> response = service.getReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        LOGGER.info("Obtain information from a reservation with {}", id);
        ReservationDTO response = service.getReservationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @RateLimiter(name = "post-reservation", fallbackMethod = "fallbackPost")
    public ResponseEntity<ReservationDTO> save(@Valid @RequestBody ReservationDTO reservation) {
        LOGGER.info("Saving new reservation");
        ReservationDTO response = service.save(reservation);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(@Min(1) @PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservation) {
        LOGGER.info("Updating a reservation with {}", id);
        ReservationDTO response = service.update(id, reservation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Min(1) @PathVariable Long id) {
        LOGGER.info("Deleting a reservation with id {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<ReservationDTO> fallbackPost(ReservationDTO reservation, RequestNotPermitted e) {
        LOGGER.debug("calling to fallbackPost");
        throw new CustomException(APIError.EXCEED_NUMBER_REQUEST);
    }

}
