package dk.dtu.software.group8.Exceptions;

public class TooManyActivitiesException extends Exception {
    public TooManyActivitiesException(String errorMessage){
        super(errorMessage);
    }
}