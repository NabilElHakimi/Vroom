package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle , Long> {

            @Query("SELECT v FROM Vehicle v INNER JOIN  v.vehicleImages ORDER BY v.createdAt DESC")
            Page<Vehicle> findAllWithImages(Pageable pageable);

            @Query("SELECT v FROM Vehicle v LEFT JOIN FETCH v.vehicleImages WHERE v.id = :id")
            Optional<Vehicle> findByIdWithImages(Long id);

}

