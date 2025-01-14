package me.elhakimi.vroom.service.impl;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.domain.enums.VehicleStatus;
import me.elhakimi.vroom.repository.VehicleRepository;
import me.elhakimi.vroom.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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






}
