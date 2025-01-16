package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.domain.VehicleImages;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;

public interface VehicleImagesRepository extends JpaRepository<VehicleImages , Long> {



}
