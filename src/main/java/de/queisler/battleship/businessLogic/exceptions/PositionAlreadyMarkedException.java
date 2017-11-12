package de.queisler.battleship.businessLogic.exceptions;

public class PositionAlreadyMarkedException extends Exception {

    public PositionAlreadyMarkedException(String message){
        super(message);
    }
}
