package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.dto.user.response.LocationWithVehiclesResponseDTO;
import me.elhakimi.vroom.exception.exceptions.LocationExceptions.LocationAlreadyExistException;
import me.elhakimi.vroom.exception.exceptions.LocationExceptions.LocationNotFoundException;
import me.elhakimi.vroom.repository.LocationRepository;
import me.elhakimi.vroom.utils.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LocationServiceImpl {

    private final LocationRepository locationRepository;

    public Location save(Location location) {

        Location checkLocation = locationRepository.findByName(location.getName());
        if (checkLocation != null) {
                throw new LocationAlreadyExistException(location.getName());
        }

        return locationRepository.save(location);
    }

    public LocationWithVehiclesResponseDTO findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));
        return LocationWithVehiclesResponseDTO.from(location);
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

    public List<Location> findByUserId() {
        AppUser user = UserUtil.getAuthenticatedUser();
        return locationRepository.findByUserId(user.getId());

    }

}
