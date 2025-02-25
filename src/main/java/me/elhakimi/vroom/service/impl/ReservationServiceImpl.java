package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Reservation;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.dto.user.request.ReservationRequestDTO;
import me.elhakimi.vroom.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import static me.elhakimi.vroom.utils.DatesUtil.getDifferenceInDays;
import static me.elhakimi.vroom.utils.UserUtil.getAuthenticatedUser;

@Service
@AllArgsConstructor
public class ReservationServiceImpl {

    private final ReservationRepository reservationRepository;
    private final VehicleServiceImpl vehicleService;

    public Reservation save(ReservationRequestDTO reservationDTO) {

        Vehicle vehicle = vehicleService.findById(reservationDTO.vehicleId());

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        AppUser appUser = getAuthenticatedUser();

        Reservation reservation = new Reservation();
        reservation.setUser(appUser);
        reservation.setVehicle(vehicle);
        reservation.setStartDate(reservationDTO.startDate());
        reservation.setEndDate(reservationDTO.endDate());
        reservation.setTotalPrice(
                vehicle.getPricePerDay() * getDifferenceInDays(reservationDTO.startDate(), reservationDTO.endDate()));

        return reservationRepository.save(reservation);



    }


}
