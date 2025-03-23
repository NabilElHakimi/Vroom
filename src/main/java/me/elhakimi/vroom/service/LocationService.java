package me.elhakimi.vroom.service;

import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.dto.user.response.LocationWithVehiclesResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public  interface LocationService {
    Location save(Location location);

    LocationWithVehiclesResponseDTO findById(Long id);

    void deleteById(Long id);

    Location update(Location location);

    Page<LocationWithVehiclesResponseDTO> findAll(Pageable pageable);

    Location findByName(String name);

    List<Location> findByUserId();
}
