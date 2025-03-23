package me.elhakimi.vroom.reservation;

import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.dto.user.request.ReservationRequestDTO;
import me.elhakimi.vroom.exception.exceptions.ReservationException.EndDateMustBeAfterStartDate;
import me.elhakimi.vroom.exception.exceptions.ReservationException.ReservationMustBeAtLeastOneDay;
import me.elhakimi.vroom.exception.exceptions.ReservationException.StartDateMustBeAfterCurrentDate;
import me.elhakimi.vroom.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private ReservationRequestDTO validReservationDTO;
    private Vehicle vehicle;
    private AppUser authenticatedUser;

    @BeforeEach
    void setUp() {
        validReservationDTO = new ReservationRequestDTO(
                1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3)
        );

        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setPrice(100.0);

        authenticatedUser = new AppUser();
        authenticatedUser.setId(1L);
    }


    @Test
    void save_EndDateBeforeStartDate_ThrowsEndDateMustBeAfterStartDate() {
        ReservationRequestDTO invalidReservationDTO = new ReservationRequestDTO(
                1L,
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(1)
        );

        assertThrows(EndDateMustBeAfterStartDate.class, () -> reservationService.save(invalidReservationDTO));
    }

    @Test
    void save_StartDateBeforeCurrentDate_ThrowsStartDateMustBeAfterCurrentDate() {
        ReservationRequestDTO invalidReservationDTO = new ReservationRequestDTO(
                1L,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        );

        assertThrows(StartDateMustBeAfterCurrentDate.class, () -> reservationService.save(invalidReservationDTO));
    }

    @Test
    void save_ReservationDurationLessThanOneDay_ThrowsReservationMustBeAtLeastOneDay() {
        ReservationRequestDTO invalidReservationDTO = new ReservationRequestDTO(
                1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(1)
        );

        assertThrows(ReservationMustBeAtLeastOneDay.class, () -> reservationService.save(invalidReservationDTO));
    }

}