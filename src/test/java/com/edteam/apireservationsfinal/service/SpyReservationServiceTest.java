package com.edteam.apireservationsfinal.service;

import com.edteam.apireservationsfinal.connector.CatalogConnector;
import com.edteam.apireservationsfinal.dto.ReservationDTO;
import com.edteam.apireservationsfinal.enums.APIError;
import com.edteam.apireservationsfinal.exception.CustomException;
import com.edteam.apireservationsfinal.model.Reservation;
import com.edteam.apireservationsfinal.repository.ReservationRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;
import java.util.logging.Logger;

import static com.edteam.apireservationsfinal.util.ReservationUtil.getReservation;
import static com.edteam.apireservationsfinal.util.ReservationUtil.getReservationDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tags(@Tag("service"))
@DisplayName("Check the functionality of the service")
class SpyReservationServiceTest {

    private static final Logger LOGGER = Logger.getLogger(SpyReservationServiceTest.class.getName());

    @Spy
    ReservationRepository repository;
    @Mock
    ConversionService conversionService;
    @Mock
    CatalogConnector catalogConnector;

    @BeforeEach
    void initialize_each_test() {
        LOGGER.info("Initializing the context on each test");
        MockitoAnnotations.openMocks(this);
    }

    @Tag("error-case")
    @DisplayName("should not return the information of the reservation")
    @Test
    void getReservation_should_not_return_the_information() {
        // GIVEN
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        when(repository.getReservationById(6L)).thenReturn(Optional.empty());
        // WHEN
        CustomException exception = assertThrows(CustomException.class, () -> {
            service.getReservationById(6L);
        });
        // THEN

        verify(repository, atMostOnce()).getReservationById(6L);
        verify(conversionService, never());

        assertAll(() -> assertNotNull(exception),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getMessage(), exception.getDescription()),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getHttpStatus(), exception.getStatus()));
    }

    @Tag("success-case")
    @DisplayName("should return the information of the reservation")
    @Test
    void getReservation_should_return_the_information() {
        // GIVEN
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        Reservation reservationModel = getReservation(1L, "EZE", "MIA");

        ReservationDTO reservationDTO = getReservationDTO(1L, "EZE", "MIA");
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);

        // WHEN
        ReservationDTO result = service.getReservationById(1L);
        // THEN
        verify(repository, atMostOnce()).getReservationById(1L);
        verify(catalogConnector, never()).getCity(any());
        verify(conversionService, atMostOnce()).convert(reservationModel, ReservationDTO.class);
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(1L, "EZE", "MIA"), result));
    }

}
