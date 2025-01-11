package me.elhakimi.vroom.web.controllers.auth;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarsController {

    @GetMapping
    public String getCars() {
        return "Cars";
    }
}
