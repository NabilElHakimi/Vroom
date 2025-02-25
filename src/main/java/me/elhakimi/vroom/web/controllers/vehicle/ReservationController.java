package me.elhakimi.vroom.web.controllers.vehicle;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Reservation;
import me.elhakimi.vroom.dto.user.request.ReservationRequestDTO;
import me.elhakimi.vroom.dto.user.response.ReservationResponseDTO;
import me.elhakimi.vroom.service.impl.ReservationServiceImpl;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
