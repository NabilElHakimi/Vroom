package me.elhakimi.vroom.web.controllers.vehicle;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.dto.user.request.VehicleWithLocationRequestDTO;
import me.elhakimi.vroom.dto.user.response.UserDetailsResponseDTO;
import me.elhakimi.vroom.dto.user.response.VehicleImagesResponseDTO;
import me.elhakimi.vroom.dto.user.response.VehicleWithLocationResponseDTO;
import me.elhakimi.vroom.service.impl.VehicleServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {

    private final VehicleServiceImpl vehicleServiceImpl;

    @PostMapping
    public ResponseEntity<Object> addVehicle(
            @RequestParam("vehicle") String vehicleJson,
            @RequestParam("images") MultipartFile[] images) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        VehicleWithLocationRequestDTO vehicleDTO = objectMapper.readValue(vehicleJson, VehicleWithLocationRequestDTO.class);

        Vehicle savedVehicle = vehicleServiceImpl.save(vehicleDTO, images);

        if (savedVehicle != null) {
            return ResponseEntity.ok(
                    VehicleWithLocationResponseDTO.from(
                            savedVehicle,
                            UserDetailsResponseDTO.from(savedVehicle.getUser()),
                            VehicleImagesResponseDTO.from(savedVehicle.getVehicleImages())
                    )
            );
        } else {
            return ResponseEntity.badRequest().body("Failed to save vehicle.");
        }
    }

    @PostMapping("/update")
        public ResponseEntity<Object> updateVehicle(@RequestBody Vehicle vehicle) {

            Vehicle updateVehicle = vehicleServiceImpl.update(vehicle);
            if(updateVehicle != null) {
                return ResponseEntity.ok(VehicleWithLocationResponseDTO.from(updateVehicle , UserDetailsResponseDTO.from(updateVehicle.getUser()) , VehicleImagesResponseDTO.from(updateVehicle.getVehicleImages())));
            }
            else {
                return ResponseEntity.badRequest().body(vehicle);
            }
        }

    @DeleteMapping("/archive/{id}")
    public ResponseEntity<Map<String , String>> archiveVehicle(@PathVariable Long id) {
        vehicleServiceImpl.archive(id);
        return ResponseEntity.ok(Map.of("message" , "Vehicle archived successfully"));
    }

    @GetMapping("/unArchive/{id}")
    public ResponseEntity<Object> deArchiveVehicle(@PathVariable Long id) {
        vehicleServiceImpl.unArchive(id);
        return ResponseEntity.ok("Vehicle unarchived successfully");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteVehicle(@PathVariable Long id) {
        vehicleServiceImpl.delete(id);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }

    @GetMapping("/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> approveVehicle(@PathVariable Long id) {
        Vehicle vehicle = vehicleServiceImpl.approve(id);
        return ResponseEntity.ok(VehicleWithLocationResponseDTO.from(vehicle , UserDetailsResponseDTO.from(vehicle.getUser()) , VehicleImagesResponseDTO.from(vehicle.getVehicleImages())));
    }

    @GetMapping("/reject/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> rejectVehicle(@PathVariable Long id) {
        Vehicle vehicle = vehicleServiceImpl.reject(id);
        return ResponseEntity.ok(VehicleWithLocationResponseDTO.from(vehicle , UserDetailsResponseDTO.from(vehicle.getUser()) , VehicleImagesResponseDTO.from(vehicle.getVehicleImages())));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findVehicle(@PathVariable Long id) {
        Vehicle vehicle = vehicleServiceImpl.findById(id);
        return ResponseEntity.ok(VehicleWithLocationResponseDTO.from(vehicle , UserDetailsResponseDTO.from(vehicle.getUser()) , VehicleImagesResponseDTO.from(vehicle.getVehicleImages())));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllVehicles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size) {
        PageRequest pageable = PageRequest.of(page-1, size);

        return ResponseEntity.ok(vehicleServiceImpl.findAll(pageable).map(
                vehicle ->
                        VehicleWithLocationResponseDTO.from(vehicle , UserDetailsResponseDTO.from(vehicle.getUser()) ,
                                VehicleImagesResponseDTO.from(vehicle.getVehicleImages()))));

    }

}
