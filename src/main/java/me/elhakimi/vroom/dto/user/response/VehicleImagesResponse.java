package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.VehicleImages;

import java.util.List;
import java.util.stream.Collectors;

public record VehicleImagesResponse(
        Long id,
        String imageUrl
){
    public static List<VehicleImagesResponse> from(List<VehicleImages> vehicleImages){
        return vehicleImages.stream()
                .map(image -> new VehicleImagesResponse(
                        image.getId(),
                        image.getImage_url()
                ))
                .collect(Collectors.toList());
    }
}
