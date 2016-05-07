package dk.dtu.software.group8.Exceptions;

/**
 * Created by Tobias
 */
public class WrongDateException extends Exception {
    public WrongDateException(String errorMessage){
        super(errorMessage);
    }
}
