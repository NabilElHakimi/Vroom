package me.elhakimi.vroom.service;

import me.elhakimi.vroom.domain.Reservation;
import me.elhakimi.vroom.dto.user.request.ReservationRequestDTO;
import me.elhakimi.vroom.dto.user.response.ReservationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    ReservationResponseDTO save(ReservationRequestDTO reservationDTO);

    List<Reservation> findAllByVehicleId(Long vehicleId);

    Page<ReservationResponseDTO> findByLocationId(Long id, Pageable pageable);

    ReservationResponseDTO updateStatus(Long id, String status);

    List<ReservationResponseDTO> findAllByVehicle_IdAndStartDateAfter(Long vehicleId);
}
