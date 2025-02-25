package me.elhakimi.vroom.web.controllers.vehicle;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.service.impl.LocationServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationServiceImpl locationService ;


    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location){
        return ResponseEntity.ok(locationService.save(location));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(locationService.findAll(pageable));
    }

}
