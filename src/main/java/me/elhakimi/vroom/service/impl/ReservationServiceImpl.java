package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Reservation;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.domain.enums.ReservationStatus;
import me.elhakimi.vroom.dto.user.request.ReservationRequestDTO;
import me.elhakimi.vroom.dto.user.response.ReservationResponseDTO;
import me.elhakimi.vroom.exception.exceptions.ReservationException.EndDateMustBeAfterStartDate;
import me.elhakimi.vroom.exception.exceptions.ReservationException.ReservationMustBeAtLeastOneDay;
import me.elhakimi.vroom.exception.exceptions.ReservationException.StartDateMustBeAfterCurrentDate;
import me.elhakimi.vroom.exception.exceptions.ReservationException.VehicleNotAvailableException;
import me.elhakimi.vroom.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


        if (!checkDates(reservationDTO.startDate(), reservationDTO.endDate())) {
            throw new EndDateMustBeAfterStartDate();
        }

        if (!checkDateIfValid(reservationDTO.startDate())) {
            throw new StartDateMustBeAfterCurrentDate();
        }

        if (!checkDateIfHaveOneDayDifference(reservationDTO.startDate(), reservationDTO.endDate())) {
            throw new ReservationMustBeAtLeastOneDay();
        }

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


    private boolean checkIfVehicleIsAvailable(List<Reservation> reservations , LocalDateTime startDate, LocalDateTime endDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getStatus().equals(ReservationStatus.APPROVED) || reservation.getStatus().equals(ReservationStatus.PENDING)) {
                if (startDate.isBefore(reservation.getEndDate().plusDays(1)) && endDate.isAfter(reservation.getStartDate())) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean checkDates(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }

    private boolean checkDateIfValid(LocalDateTime startDate) {
        return startDate.isAfter(LocalDateTime.now().minusDays(1));
    }

    private boolean checkDateIfHaveOneDayDifference(LocalDateTime startDate, LocalDateTime endDate) {
        return getDifferenceInDays(startDate, endDate) >= 1;
    }

    public List<Reservation> findAllByVehicleId(Long vehicleId) {
        return reservationRepository.findAllByVehicleId(vehicleId);
    }

    public Page<ReservationResponseDTO> findByLocationId(Long id, Pageable pageable) {
        return reservationRepository.findReservationsByLocation(id, pageable)
                .map(ReservationResponseDTO::from);
    }

    public ReservationResponseDTO updateStatus(Long id, String status) {

        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

       if(reservation.getStatus().equals(ReservationStatus.valueOf(status))) {
           throw new IllegalArgumentException("Reservation already in this status");
       }

        AppUser appUser = getAuthenticatedUser();

        if (!reservation.getVehicle().getLocation().getUser().getId().equals(appUser.getId())) {
            throw new IllegalArgumentException("You are not allowed to update this reservation");
        }


        reservation.setStatus(ReservationStatus.valueOf(status));

        return ReservationResponseDTO.from(reservationRepository.save(reservation));


    }


    public List<ReservationResponseDTO> findAllByVehicle_IdAndStartDateAfter(Long vehicleId) {
        return reservationRepository.findAllByVehicle_IdAndStartDateAfter(vehicleId, LocalDateTime.now())
                .stream()
                .map(ReservationResponseDTO::from)
                .toList();
    }


}
