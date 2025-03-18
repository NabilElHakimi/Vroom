package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.Reservation;
import me.elhakimi.vroom.domain.enums.ReservationStatus;
import me.elhakimi.vroom.dto.user.request.VehicleWithLocationRequestDTO;

import java.time.LocalDateTime;

public record ReservationResponseDTO(

        Long id ,
        UserDetailsResponseDTO user,
        VehicleWithLocationRequestDTO vehicle,
        LocalDateTime startDate,
        LocalDateTime endDate,
        double totalPrice,
        ReservationStatus status
) {

    public static ReservationResponseDTO from(Reservation reservation){
        return new ReservationResponseDTO(
                reservation.getId(),
                UserDetailsResponseDTO.from(reservation.getUser()),
                VehicleWithLocationRequestDTO.from(reservation.getVehicle(), UserDetailsResponseDTO.from(reservation.getUser()), null),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getTotalPrice(),
                reservation.getStatus()
        );
    }


}
