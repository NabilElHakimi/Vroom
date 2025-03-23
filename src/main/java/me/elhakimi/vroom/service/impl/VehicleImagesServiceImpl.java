package me.elhakimi.vroom.service.impl;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.VehicleImages;
import me.elhakimi.vroom.repository.VehicleImagesRepository;
import me.elhakimi.vroom.service.VehicleImagesService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehicleImagesServiceImpl implements VehicleImagesService {

    private final VehicleImagesRepository vehicleImagesRepository;

    public void save(VehicleImages vehicleImage) {
        vehicleImagesRepository.save(vehicleImage);
    }
}
