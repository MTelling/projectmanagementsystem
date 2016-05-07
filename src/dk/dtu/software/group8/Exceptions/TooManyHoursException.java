package dk.dtu.software.group8.Exceptions;

/**
 * Created by Morten
 */
public class TooManyHoursException extends Exception {
    public TooManyHoursException(String errorMessage){
        super(errorMessage);
    }
}
