package me.elhakimi.vroom.web.admin;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.dto.user.response.UserDetails;
import me.elhakimi.vroom.dto.user.response.VehicleResponse;
import me.elhakimi.vroom.service.impl.VehicleServiceImpl;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;

@RestController
@RequestMapping("/admin/vehicles")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class VehicleController {


        private final VehicleServiceImpl vehicleServiceImpl;

        @PostMapping
        public ResponseEntity<Object> addVehicle(
                @RequestParam("vehicle") String vehicleJson, // Recevoir les données JSON en tant que chaîne
                @RequestParam("images") MultipartFile[] images) throws IOException { // Recevoir les fichiers

            // Convertir la chaîne JSON en objet Vehicle
            ObjectMapper objectMapper = new ObjectMapper();
            Vehicle vehicle = objectMapper.readValue(vehicleJson, Vehicle.class);

            // Appeler votre service pour enregistrer le véhicule et les images
            Vehicle saveVehicle = vehicleServiceImpl.save(vehicle, images);

            if (saveVehicle != null) {
                return ResponseEntity.ok(VehicleResponse.from(saveVehicle, UserDetails.from(saveVehicle.getUser())));
            } else {
                return ResponseEntity.badRequest().body(vehicle);
            }
        }

    @PostMapping("/update")
        public ResponseEntity<Object> updateVehicle(@RequestBody Vehicle vehicle) {

            Vehicle updateVehicle = vehicleServiceImpl.update(vehicle);
            if(updateVehicle != null) {
                return ResponseEntity.ok(VehicleResponse.from(updateVehicle , UserDetails.from(updateVehicle.getUser())));
            }
            else {
                return ResponseEntity.badRequest().body(vehicle);
            }
        }

        @PostMapping("/archive")

        public ResponseEntity<Object> archiveVehicle(@RequestBody Long id) {
            vehicleServiceImpl.archive(id);
            return ResponseEntity.ok("Vehicle archived successfully");
        }

        @PostMapping("/delete")

        public ResponseEntity<Object> deleteVehicle(@RequestBody Long id) {
            vehicleServiceImpl.delete(id);
            return ResponseEntity.ok("Vehicle deleted successfully");
        }

        @PostMapping("/approve")

        public ResponseEntity<Object> approveVehicle(@RequestBody Long id) {
            Vehicle vehicle = vehicleServiceImpl.approve(id);
            return ResponseEntity.ok(VehicleResponse.from(vehicle , UserDetails.from(vehicle.getUser())));
        }

        @PostMapping("/reject")

        public ResponseEntity<Object> rejectVehicle(@RequestBody Long id) {
            Vehicle vehicle = vehicleServiceImpl.reject(id);
            return ResponseEntity.ok(VehicleResponse.from(vehicle , UserDetails.from(vehicle.getUser())));
        }

        @PostMapping("/find")
        public ResponseEntity<Object> findVehicle(@RequestBody Long id) {
            Vehicle vehicle = vehicleServiceImpl.findById(id);
            return ResponseEntity.ok(VehicleResponse.from(vehicle , UserDetails.from(vehicle.getUser())));
        }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllVehicles(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(vehicleServiceImpl.findAll(pageable).map(
                vehicle ->
                        VehicleResponse.from(vehicle , UserDetails.from(vehicle.getUser()))));
    }
}
