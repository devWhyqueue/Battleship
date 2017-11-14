package de.queisler.battleship.businessLogic.exceptions;

public class InvalidPositionException extends Exception {
    public InvalidPositionException(String message) {
        super(message);
    }
}
