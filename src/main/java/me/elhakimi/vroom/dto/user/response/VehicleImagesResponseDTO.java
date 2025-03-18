package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.VehicleImages;

import java.util.List;
import java.util.stream.Collectors;

public record VehicleImagesResponseDTO(
        Long id,
        String imageUrl
){
    public static List<VehicleImagesResponseDTO> from(List<VehicleImages> vehicleImages){
        return vehicleImages.stream()
                .map(image -> new VehicleImagesResponseDTO(
                        image.getId(),
                        image.getImage_url()
                ))
                .collect(Collectors.toList());
    }
}
