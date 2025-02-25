package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
