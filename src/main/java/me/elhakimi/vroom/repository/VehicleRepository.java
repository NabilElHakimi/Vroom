package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle , Long> {


}
