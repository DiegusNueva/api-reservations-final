package com.edteam.apireservationsfinal.repository;

import com.edteam.apireservationsfinal.model.Reservation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.quickperf.annotation.ExpectMaxExecutionTime;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.jvm.annotations.MeasureHeapAllocation;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.edteam.apireservationsfinal.util.ReservationUtil.getReservation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@QuickPerfTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags(@Tag("performance"))
@DisplayName("Check the functionality of the repository")
class PerformanceReservationRepositoryTest {

    private static final Logger LOGGER = Logger.getLogger(PerformanceReservationRepositoryTest.class.getName());

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

    @MeasureHeapAllocation
    //@ExpectMaxExecutionTime(milliSeconds = 500)
    @Tag("success-case")
    @DisplayName("should return the information of the reservation with heap")
    @Test
    void getReservation_should_return_the_information_with_heap() {
        // GIVEN
        ReservationRepository repository = new ReservationRepository();
        // WHEN
        Optional<Reservation> result = repository.getReservationById(1L);
        // THEN
        assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
    }

}
