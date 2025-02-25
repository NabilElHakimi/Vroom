package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.dto.user.response.LocationWithVehiclesResponseDTO;
import me.elhakimi.vroom.exception.exceptions.LocationExceptions.LocationAlreadyExistException;
import me.elhakimi.vroom.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LocationServiceImpl {

    private final LocationRepository locationRepository;

    public Location save(Location location) {

        Location checkLocation = locationRepository.findByName(location.getName());
        if (checkLocation != null) {
                throw new LocationAlreadyExistException( location.getName());
        }

        return locationRepository.save(location);
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    public Location update(Location location) {
        return locationRepository.save(location);
    }

    public Page<LocationWithVehiclesResponseDTO> findAll(Pageable pageable) {
        Page<Location> locations =  locationRepository.findAll(pageable);
        return locations.map(LocationWithVehiclesResponseDTO::from);

    }

    public Location findByName(String name) {
        return locationRepository.findByName(name);
    }




}
