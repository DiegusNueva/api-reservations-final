package com.edteam.apireservationsfinal.repository;

import com.edteam.apireservationsfinal.model.Reservation;
import org.junit.*;

import java.util.Optional;
import java.util.logging.Logger;

import static com.edteam.apireservationsfinal.util.ReservationUtil.getReservation;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldReservationRepositoryTest {

    private static final Logger LOGGER = Logger.getLogger(OldReservationRepositoryTest.class.getName());

    @Before
    public void initialize_each_test() {
        LOGGER.info("Initializing the context on each test");

        // assertEquals(1, repository.getReservations().size());

    }

    @After
    public void destroy_each_test() {
        LOGGER.info("Destroy the context on each test");
    }

    @BeforeClass
    public static void initialize_all_test() {
        LOGGER.info("Initialize the context on all test");
    }

    @AfterClass
    public static void destroy_all_test() {
        LOGGER.info("Destroy the context on all test");
    }

    @Test
    public void getReservation_should_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(1L);

        // Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(getReservation(1L, "EZE", "MIA"), result.get());

    }

}
