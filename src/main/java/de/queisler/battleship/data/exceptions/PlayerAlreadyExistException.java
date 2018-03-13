package de.queisler.battleship.data.exceptions;

public final class PlayerAlreadyExistException extends Exception
{
	private static final long serialVersionUID = 1L;

	public PlayerAlreadyExistException(String message)
	{
		super(message);
	}
}