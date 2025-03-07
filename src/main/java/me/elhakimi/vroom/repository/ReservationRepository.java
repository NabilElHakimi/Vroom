package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByVehicleId(Long vehicleId);


    @Query(value = "SELECT r.* " +
            "FROM reservation r " +
            "JOIN vehicle v ON r.vehicle_id = v.id " +
            "JOIN location l ON v.location_id = l.id " +
            "WHERE l.id = :locationId",
            countQuery = "SELECT COUNT(*) FROM reservation r " +
                    "JOIN vehicle v ON r.vehicle_id = v.id " +
                    "JOIN location l ON v.location_id = l.id " +
                    "WHERE l.id = :locationId",
            nativeQuery = true)
    Page<Reservation> findReservationsByLocation(@Param("locationId") Long locationId, Pageable pageable);

}
