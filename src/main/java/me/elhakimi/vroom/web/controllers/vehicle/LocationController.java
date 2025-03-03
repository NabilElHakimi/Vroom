package me.elhakimi.vroom.web.controllers.vehicle;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Location;
import me.elhakimi.vroom.dto.user.response.LocationWithVehiclesResponseDTO;
import me.elhakimi.vroom.dto.user.response.LocationWithoutVehicleResponseDTO;
import me.elhakimi.vroom.service.impl.LocationServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationServiceImpl locationService ;


    @PostMapping
    public ResponseEntity<Location> save(@Valid @RequestBody Location location ){
        return ResponseEntity.ok(locationService.save(location));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(locationService.findAll(pageable));
    }


    @GetMapping("/find-by-user")
    public ResponseEntity<List<LocationWithoutVehicleResponseDTO>> findByUserId() {
        List<Location> locations = locationService.findByUserId();
        List<LocationWithoutVehicleResponseDTO> responseDTOs = locations.stream()
                .map(LocationWithoutVehicleResponseDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationWithVehiclesResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(locationService.findById(id));
    }

}
