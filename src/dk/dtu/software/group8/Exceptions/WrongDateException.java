package dk.dtu.software.group8.Exceptions;

public class WrongDateException extends Exception {

    public WrongDateException(String ErrorMessage){
        super(ErrorMessage);
    }
}