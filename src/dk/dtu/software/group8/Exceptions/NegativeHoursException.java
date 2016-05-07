package dk.dtu.software.group8.Exceptions;

/**
 * Created by Marcus
 */
public class NegativeHoursException extends Exception {
    public NegativeHoursException(String errorMessage){
        super(errorMessage);
    }
}