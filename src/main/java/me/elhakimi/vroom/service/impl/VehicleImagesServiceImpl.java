package me.elhakimi.vroom.service.impl;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.VehicleImages;
import me.elhakimi.vroom.repository.VehicleImagesRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehicleImagesServiceImpl {

    private final VehicleImagesRepository vehicleImagesRepository;

    public void save(VehicleImages vehicleImage) {
        vehicleImagesRepository.save(vehicleImage);
    }
}
