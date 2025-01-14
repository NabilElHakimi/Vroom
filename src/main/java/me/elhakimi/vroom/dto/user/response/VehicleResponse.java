package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.*;
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
        List<VehicleImages> articleImages,
        UserDetails userDetails,
        List<Likes> likes,
        City city,
        Model model,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static VehicleResponse from(Vehicle vehicle, UserDetails userDetails) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getTitle(),
                vehicle.getDescription(),
                vehicle.getTelephone(),
                vehicle.getPrice(),
                vehicle.isPublished(),
                vehicle.isArchived(),
                vehicle.getStatus(),
                vehicle.getArticleImages(),
                userDetails,
                vehicle.getLikes(),
                vehicle.getCity(),
                vehicle.getModel(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}