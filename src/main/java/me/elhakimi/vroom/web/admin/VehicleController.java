package me.elhakimi.vroom.web.admin;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Vehicle;
import me.elhakimi.vroom.dto.user.response.UserDetails;
import me.elhakimi.vroom.dto.user.response.VehicleResponse;
import me.elhakimi.vroom.service.impl.VehicleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/vehicles")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class VehicleController {


        private final VehicleServiceImpl vehicleServiceImpl;

        @PostMapping
        public ResponseEntity<Object> addVehicle(@RequestBody Vehicle vehicle) {

            Vehicle saveVehicle = vehicleServiceImpl.save(vehicle);
            if(saveVehicle != null) {
                return ResponseEntity.ok(VehicleResponse.from(saveVehicle , UserDetails.from(saveVehicle.getUser())));
            }
            else {
                return ResponseEntity.badRequest().body(vehicle);
            }
        }

}
