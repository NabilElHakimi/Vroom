package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.*;
import me.elhakimi.vroom.domain.enums.FuelType;
import me.elhakimi.vroom.domain.enums.VehicleStatus;

import java.time.LocalDateTime;
import java.util.List;

public record VehicleResponse (
        Long id,
        String title,
        String description,
        String telephone,
        double price,
        boolean isPublished,
        boolean isArchived,
        VehicleStatus status,
        List<VehicleImagesResponse> articleImages,
        UserDetails userDetails,
        List<Likes> likes,
        String city,
        Model model,
        FuelType fuelType,
        Long mileage,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static VehicleResponse from(Vehicle vehicle, UserDetails userDetails , List<VehicleImagesResponse> VehicleImagesResponse) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getTitle(),
                vehicle.getDescription(),
                vehicle.getTelephone(),
                vehicle.getPrice(),
                vehicle.isPublished(),
                vehicle.isArchived(),
                vehicle.getStatus(),
                VehicleImagesResponse,
                userDetails,
                vehicle.getLikes(),
                vehicle.getCity(),
                vehicle.getModel(),
                vehicle.getFuelType(),
                vehicle.getMileage(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}