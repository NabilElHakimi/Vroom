package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.*;
import me.elhakimi.vroom.domain.enums.FuelType;
import me.elhakimi.vroom.domain.enums.VehicleStatus;

import java.time.LocalDateTime;
import java.util.List;

public record VehicleWithLocationResponseDTO(
        Long id,
        String codeCar ,
        String mark,
        String model,
        String description,
        String telephone,
        double price,
        boolean isPublished,
        boolean isArchived,
        VehicleStatus status,
        List<VehicleImagesResponseDTO> articleImages,
        UserDetailsResponseDTO userDetails,
        List<Likes> likes,
        String city,
        FuelType fuelType,
        int year,
        LocationWithoutVehicleResponseDTO location,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static VehicleWithLocationResponseDTO from(Vehicle vehicle, UserDetailsResponseDTO userDetailsResponseDTO, List<VehicleImagesResponseDTO> VehicleImagesResponse) {
        return new VehicleWithLocationResponseDTO(
                vehicle.getId(),
                vehicle.getCodeCar(),
                vehicle.getMark(),
                vehicle.getModel(),
                vehicle.getDescription(),
                vehicle.getTelephone(),
                vehicle.getPrice(),
                vehicle.isPublished(),
                vehicle.isArchived(),
                vehicle.getStatus(),
                VehicleImagesResponse,
                userDetailsResponseDTO,
                vehicle.getLikes(),
                vehicle.getCity(),
                vehicle.getFuelType(),
                vehicle.getYear(),
                vehicle.getLocation() != null ? LocationWithoutVehicleResponseDTO.from(vehicle.getLocation()) : null,
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}