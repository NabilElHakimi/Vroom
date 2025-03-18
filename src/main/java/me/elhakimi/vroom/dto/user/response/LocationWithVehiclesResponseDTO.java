package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.domain.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

public record LocationWithVehiclesResponseDTO(
        Long id,
        String name,
        String address,
        String city,
        String telephone,
        String email,
        List<VehicleWithOutLocationResponseDTO> vehicles ,
        UserDetailsResponseDTO user
) {
    public static LocationWithVehiclesResponseDTO from(Location location) {
        List<VehicleWithOutLocationResponseDTO> vehicleDTOs = location.getVehicles().stream()
                .map(VehicleWithOutLocationResponseDTO::from)
                .collect(Collectors.toList());

        return new LocationWithVehiclesResponseDTO(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCity(),
                location.getTelephone(),
                location.getEmail(),
                vehicleDTOs ,
                UserDetailsResponseDTO.from(location.getUser())
        );
    }
}