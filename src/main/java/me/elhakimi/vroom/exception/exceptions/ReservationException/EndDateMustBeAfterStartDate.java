package me.elhakimi.vroom.exception.exceptions.ReservationException;

public class EndDateMustBeAfterStartDate extends RuntimeException {
    public EndDateMustBeAfterStartDate() {
        super("End date must be after start date");
    }
}
