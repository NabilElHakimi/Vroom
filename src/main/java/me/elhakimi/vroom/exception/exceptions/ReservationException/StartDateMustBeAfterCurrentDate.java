package me.elhakimi.vroom.exception.exceptions.ReservationException;

public class StartDateMustBeAfterCurrentDate extends RuntimeException {
    public StartDateMustBeAfterCurrentDate(){
        super("Start date must be after current date");
    }
}
