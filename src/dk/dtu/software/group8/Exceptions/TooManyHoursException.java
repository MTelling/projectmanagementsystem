package dk.dtu.software.group8.Exceptions;

public class TooManyHoursException extends Exception {

    public TooManyHoursException(String errorMessage){
        super(errorMessage);
    }
}
