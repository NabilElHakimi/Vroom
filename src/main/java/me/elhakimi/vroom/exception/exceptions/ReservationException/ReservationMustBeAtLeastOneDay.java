package me.elhakimi.vroom.exception.exceptions.ReservationException;

public class ReservationMustBeAtLeastOneDay extends RuntimeException {
    public ReservationMustBeAtLeastOneDay()
    {
        super("Reservation must be at least one day");
    }
}
