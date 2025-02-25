package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.Location;

public record LocationWithVehiclesResponseDTO(
        Long id,
        String name,
        String address,
        String city,
        String telephone,
        String email ,
        VehicleWithOutLocationResponseDTO vehicles
) {
    public static LocationWithVehiclesResponseDTO from(Location location) {
        return new LocationWithVehiclesResponseDTO(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCity(),
                location.getTelephone(),
                location.getEmail(),
                VehicleWithOutLocationResponseDTO.from(location.getVehicles())
        );
    }
}
