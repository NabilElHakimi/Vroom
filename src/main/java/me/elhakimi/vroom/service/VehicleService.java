package me.elhakimi.vroom.service;

import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.dto.user.request.VehicleWithLocationRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface VehicleService {
    Vehicle save(VehicleWithLocationRequestDTO vehicleDTO, MultipartFile[] images);

    Vehicle update(Vehicle vehicle);

    void archive(Long id);

    void unArchive(Long id);

    void delete(Long id);

    Vehicle approve(Long id);

    Vehicle reject(Long id);

    Vehicle findById(Long id);

    Page<Vehicle> findAll(Pageable pageable);
}
