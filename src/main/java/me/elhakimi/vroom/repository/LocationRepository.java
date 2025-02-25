package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByName(String name);

}
