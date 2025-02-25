package me.elhakimi.vroom.dto.user.request;

import me.elhakimi.vroom.domain.Likes;
import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.domain.enums.FuelType;
import me.elhakimi.vroom.domain.enums.VehicleStatus;
import me.elhakimi.vroom.dto.user.response.LocationWithoutVehicleResponseDTO;
import me.elhakimi.vroom.dto.user.response.UserDetailsResponseDTO;
import me.elhakimi.vroom.dto.user.response.VehicleImagesResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record VehicleWithLocationRequestDTO(
        Long id,
        String codeCar ,
        String mark,
        String model,
        String description,
        String telephone,
        double price,
        List<VehicleImagesResponseDTO> articleImages,
        String city,
        FuelType fuelType,
        Long mileage,
        int year,
        Long locationId
) {

    public static VehicleWithLocationRequestDTO from(Vehicle vehicle, UserDetailsResponseDTO userDetailsResponseDTO, List<VehicleImagesResponseDTO> VehicleImagesResponse) {
        return new VehicleWithLocationRequestDTO(
                vehicle.getId(),
                vehicle.getCodeCar(),
                vehicle.getMark(),
                vehicle.getModel(),
                vehicle.getDescription(),
                vehicle.getTelephone(),
                vehicle.getPrice(),
                VehicleImagesResponse,
                vehicle.getCity(),
                vehicle.getFuelType(),
                vehicle.getMileage(),
                vehicle.getYear(),
                vehicle.getLocation().getId()

        );
    }

    public static Vehicle toVehicle(VehicleWithLocationRequestDTO dto) {
        Location location = new Location();
        location.setId(dto.locationId());

        return Vehicle.builder()
                .id(dto.id())
                .codeCar(dto.codeCar())
                .mark(dto.mark())
                .model(dto.model())
                .description(dto.description())
                .telephone(dto.telephone())
                .price(dto.price())
                .city(dto.city())
                .fuelType(dto.fuelType())
                .mileage(dto.mileage())
                .year(dto.year())
                .location(location)
                .build();
    }
}