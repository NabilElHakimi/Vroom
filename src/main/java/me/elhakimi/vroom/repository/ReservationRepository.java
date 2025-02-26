package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByVehicleId(Long vehicleId);
}
