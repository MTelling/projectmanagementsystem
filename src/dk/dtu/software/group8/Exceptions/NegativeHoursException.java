package dk.dtu.software.group8.Exceptions;

public class NegativeHoursException extends Exception {

    public NegativeHoursException(String errorMessage){
        super(errorMessage);
    }
}