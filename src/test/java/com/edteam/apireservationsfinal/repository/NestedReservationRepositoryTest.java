package com.edteam.apireservationsfinal.repository;

import com.edteam.apireservationsfinal.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.edteam.apireservationsfinal.util.ReservationUtil.getReservation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Tags(@Tag("repository"))
@DisplayName("Check the functionality of the repository")
class NestedReservationRepositoryTest {

    private static final Logger LOGGER = Logger.getLogger(NestedReservationRepositoryTest.class.getName());

    @BeforeEach
    void initialize_each_test() {
        LOGGER.info("Initializing the context on each test");

        // assertEquals(1, repository.getReservations().size());

    }

    @AfterEach
    void destroy_each_test() {
        LOGGER.info("Destroy the context on each test");
    }

    @BeforeAll
    static void initialize_all_test() {
        LOGGER.info("Initialize the context on all test");
    }

    @AfterAll
    static void destroy_all_test() {
        LOGGER.info("Destroy the context on all test");
    }

    @Nested
    class GetReservation {

        @Tag("error-case")
        @DisplayName("should not return the information of the reservation")
        @Test
        void getReservation_should_not_return_the_information() {
            // GIVEN
            ReservationRepository repository = new ReservationRepository();
            // WHEN
            Optional<Reservation> result = repository.getReservationById(500L);
            // THEN
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isEmpty()));
        }

    }

    @Nested
    class SaveReservation {

        @Tag("success-case")
        @DisplayName("should return the information of all the reservations using external files")
        @ParameterizedTest
        @CsvFileSource(resources = "/save-repository.csv")
        void save_should_return_the_information_using_external_file(String origin, String destination) {

            // GIVEN
            ReservationRepository repository = new ReservationRepository();

            // WHEN
            Reservation result = repository.save(getReservation(null, origin, destination));

            // THEN
            assertAll(() -> assertNotNull(result),
                    () -> assertEquals(origin, result.getItinerary().getSegment().get(0).getOrigin()),
                    () -> assertEquals(destination, result.getItinerary().getSegment().get(0).getDestination()));
        }

        @Tag("success-case")
        @DisplayName("should return the information of all the reservations using CSV")
        @ParameterizedTest
        @CsvSource({ "MIA, AEP", "BUE, SCL", "BUE, MIA" })
        void save_should_return_the_information_using_csv(String origin, String destination) {

            // GIVEN
            ReservationRepository repository = new ReservationRepository();

            // WHEN
            Reservation result = repository.save(getReservation(null, origin, destination));

            // THEN
            assertAll(() -> assertNotNull(result),
                    () -> assertEquals(origin, result.getItinerary().getSegment().get(0).getOrigin()),
                    () -> assertEquals(destination, result.getItinerary().getSegment().get(0).getDestination()));
        }

        @Tag("success-case")
        @DisplayName("should return the information of saved reservation")
        @ParameterizedTest
        @ValueSource(strings = { "AEP", "MIA" })
        void save_should_return_the_information(String origin) {

            // GIVEN
            ReservationRepository repository = new ReservationRepository();

            // WHEN
            Reservation result = repository.save(getReservation(null, origin, "AEP"));

            // THEN
            assertAll(() -> assertNotNull(result),
                    () -> assertEquals(origin, result.getItinerary().getSegment().get(0).getOrigin()),
                    () -> assertEquals("AEP", result.getItinerary().getSegment().get(0).getDestination()));
        }

        @Tag("error-case")
        @DisplayName("should not return the information of the reservation by timeout")
        @Test
        @Timeout(value = 100L, unit = TimeUnit.MILLISECONDS)
        void getReservation_should_not_return_the_information_by_timeout() {
            // GIVEN
            ReservationRepository repository = new ReservationRepository();
            // WHEN
            Optional<Reservation> result = repository.getReservationById(1L);
            // THEN
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                    () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
        }

        @Tag("success-case")
        @DisplayName("should return the information of the reservation")
        @Test
        void getReservation_should_return_the_information() {
            // GIVEN
            ReservationRepository repository = new ReservationRepository();
            // WHEN
            Optional<Reservation> result = repository.getReservationById(1L);
            // THEN
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                    () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
        }

    }

}
