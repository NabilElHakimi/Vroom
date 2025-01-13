package me.elhakimi.vroom.web.admin;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/vehicles")
@PreAuthorize("hasRole('ADMIN')")
public class Vehicle {

        @PostMapping
        public String addVehicle() {
            return "Vehicle added";
        }



}
