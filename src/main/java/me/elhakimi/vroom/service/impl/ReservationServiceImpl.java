package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Reservation;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.domain.enums.ReservationStatus;
import me.elhakimi.vroom.dto.user.request.ReservationRequestDTO;
import me.elhakimi.vroom.dto.user.response.ReservationResponseDTO;
import me.elhakimi.vroom.exception.exceptions.ReservationException.VehicleNotAvailableException;
import me.elhakimi.vroom.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static me.elhakimi.vroom.utils.DatesUtil.getDifferenceInDays;
import static me.elhakimi.vroom.utils.UserUtil.getAuthenticatedUser;

@Service
@AllArgsConstructor
public class ReservationServiceImpl {

    private final ReservationRepository reservationRepository;
    private final VehicleServiceImpl vehicleService;

    public ReservationResponseDTO save(ReservationRequestDTO reservationDTO) {

        Vehicle vehicle = vehicleService.findById(reservationDTO.vehicleId());

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found");
        }


        List<Reservation> reservations = findAllByVehicleId(reservationDTO.vehicleId());

        if (!checkIfVehicleIsAvailable(reservations, reservationDTO.startDate(), reservationDTO.endDate())) {
            throw new VehicleNotAvailableException();
        }

        AppUser appUser = getAuthenticatedUser();

        Reservation reservation = new Reservation();
        reservation.setUser(appUser);
        reservation.setVehicle(vehicle);
        reservation.setStartDate(reservationDTO.startDate());
        reservation.setEndDate(reservationDTO.endDate());
        reservation.setTotalPrice(
                vehicle.getPrice() * getDifferenceInDays(reservationDTO.startDate(), reservationDTO.endDate()));
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setCreatedAt(LocalDateTime.now());

        return ReservationResponseDTO.from(reservationRepository.save(reservation));

    }


    public boolean checkIfVehicleIsAvailable(List<Reservation> reservations , LocalDateTime startDate, LocalDateTime endDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getStatus().equals(ReservationStatus.APPROVED) || reservation.getStatus().equals(ReservationStatus.PENDING)) {
                if (startDate.isBefore(reservation.getEndDate()) && endDate.isAfter(reservation.getStartDate())) {
                    return false;
                }
            }
        }
        return true;
    }


    public List<Reservation> findAllByVehicleId(Long vehicleId) {
        return reservationRepository.findAllByVehicleId(vehicleId);
    }

}
