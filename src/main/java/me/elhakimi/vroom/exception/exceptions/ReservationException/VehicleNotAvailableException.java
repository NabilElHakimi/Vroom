package me.elhakimi.vroom.exception.exceptions.ReservationException;

public class VehicleNotAvailableException extends RuntimeException {
    public VehicleNotAvailableException() {
        super("Vehicle is not available for the selected dates");
    }
}
