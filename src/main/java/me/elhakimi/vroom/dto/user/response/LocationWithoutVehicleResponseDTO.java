package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.Location;

public record LocationWithoutVehicleResponseDTO(
        Long id,
        String name,
        String address,
        String city,
        String telephone,
        String email
) {
    public static LocationWithoutVehicleResponseDTO from(Location location) {
        return new LocationWithoutVehicleResponseDTO(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCity(),
                location.getTelephone(),
                location.getEmail()
        );
    }
}