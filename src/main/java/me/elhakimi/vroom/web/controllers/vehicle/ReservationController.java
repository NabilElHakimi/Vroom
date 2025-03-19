package me.elhakimi.vroom.web.controllers.vehicle;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Reservation;
import me.elhakimi.vroom.dto.user.request.ReservationRequestDTO;
import me.elhakimi.vroom.dto.user.response.ReservationResponseDTO;
import me.elhakimi.vroom.service.impl.ReservationServiceImpl;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationServiceImpl reservationServiceImpl;

    @PostMapping
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequestDTO reservation) {
        ReservationResponseDTO savedReservation = reservationServiceImpl.save(reservation);
        if (savedReservation != null) {
            return ResponseEntity.ok(savedReservation);
        } else {
            return ResponseEntity.badRequest().body("Failed to save reservation.");
        }
    }

    @GetMapping("/get-by-location/{id}")
    public ResponseEntity<?> getReservationByLocationId(@PathVariable Long id , @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page-1, size);

        Page<ReservationResponseDTO> reservation = reservationServiceImpl.findByLocationId(id , pageable);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.badRequest().body("Reservation not found.");
        }
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        ReservationResponseDTO updatedReservation = reservationServiceImpl.updateStatus(id, status);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.badRequest().body("Failed to update reservation status.");
        }
    }

    @GetMapping("/get-by-vehicle/{id}")
    public ResponseEntity<?> getReservationByVehicleId(@PathVariable Long id) {
        List<ReservationResponseDTO> reservation = reservationServiceImpl.findAllByVehicle_IdAndStartDateAfter(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.badRequest().body("Reservation not found.");
        }
    }

}
