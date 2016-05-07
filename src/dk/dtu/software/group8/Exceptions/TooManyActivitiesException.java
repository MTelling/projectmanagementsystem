package dk.dtu.software.group8.Exceptions;

/**
 * Created by Marcus
 */
public class TooManyActivitiesException extends Exception {
    public TooManyActivitiesException(String errorMessage){
        super(errorMessage);
    }
}