package me.elhakimi.vroom.web.controllers.auth;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@RestController

@RequestMapping("/cars")
public class CarsController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String getCars() {
        return "Cars";
    }

}
