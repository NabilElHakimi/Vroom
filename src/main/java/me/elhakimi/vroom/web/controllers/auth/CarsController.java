package me.elhakimi.vroom.web.controllers.auth;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/cars")
public class CarsController {

    @GetMapping
    public String getCars() {
        return "Cars";
    }
}
