package me.elhakimi.vroom.service.impl;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.domain.enums.VehicleStatus;
import me.elhakimi.vroom.repository.VehicleRepository;
import me.elhakimi.vroom.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VehicleServiceImpl {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    public Vehicle save(Vehicle vehicle){

        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vehicle.setUser(appUser);
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setStatus(VehicleStatus.PENDING);

        return vehicleRepository.save(vehicle);

    }

    public Vehicle update(Vehicle vehicle){

        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(vehicle.getUser() != appUser) throw new RuntimeException("You are not allowed to update this vehicle");

        vehicle.setUpdatedAt(LocalDateTime.now());
        vehicle.setStatus(VehicleStatus.PENDING);

        return vehicleRepository.save(vehicle);

    }


    public void archive(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setArchived(true);
        vehicleRepository.save(vehicle);
    }

    public void delete(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    public Vehicle approve(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setStatus(VehicleStatus.PUBLISHED);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle reject(Long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setStatus(VehicleStatus.REJECTED);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle findById(Long id){
        return vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public Page<Vehicle> findAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

}
