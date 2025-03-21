package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.domain.enums.TypeRole;
import me.elhakimi.vroom.dto.user.response.LocationWithVehiclesResponseDTO;
import me.elhakimi.vroom.exception.exceptions.LocationExceptions.LocationAlreadyExistException;
import me.elhakimi.vroom.exception.exceptions.LocationExceptions.LocationNotFoundException;
import me.elhakimi.vroom.exception.exceptions.LocationExceptions.YouCantCreateMore;
import me.elhakimi.vroom.repository.LocationRepository;
import me.elhakimi.vroom.service.LocationService;
import me.elhakimi.vroom.service.UserService;
import me.elhakimi.vroom.utils.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserService userService;

    public Location save(Location location) {


        List<Location> locations = locationRepository.findAll();

        if(locations.size() > 3){
            throw new YouCantCreateMore("You can't create more than 4 locations");
        }

        locations.forEach(lo -> {
            if (lo.getName().equals(location.getName())) {
                throw new LocationAlreadyExistException(location.getName());
            }
        });

        AppUser user = UserUtil.getAuthenticatedUser();

        if(user.getRole().getName().name().equals("CLIENT")){
            user.getRole().setName(TypeRole.LEADER);
            location.setUser(user);
            userService.updateIn(user);
        }

        location.setUser(user);

        return locationRepository.save(location);
    }

    public LocationWithVehiclesResponseDTO findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));

        location.setVehicles(location.getVehicles().stream().filter(
                v-> !v.isArchived())
                .collect(Collectors.toList())) ;

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
