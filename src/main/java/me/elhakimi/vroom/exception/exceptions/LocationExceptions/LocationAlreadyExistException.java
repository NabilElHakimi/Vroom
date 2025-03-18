package me.elhakimi.vroom.exception.exceptions.LocationExceptions;

public class LocationAlreadyExistException extends RuntimeException {
    public LocationAlreadyExistException(String message) {
        super( "Location with name " + message+ " already exist");
    }
}
