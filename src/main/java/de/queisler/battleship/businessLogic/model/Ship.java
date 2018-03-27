package de.queisler.battleship.businessLogic.model;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.ShipType;
import de.queisler.battleship.businessLogic.exceptions.InvalidPositionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = { "shipType" })
public class Ship
{
	private ShipType shipType;
	@Setter
	private Position position;

	public Ship(ShipType shipType, Point startPoint, Alignment alignment) throws InvalidPositionException
	{
		this.shipType = shipType;
		this.position = new Position(startPoint, alignment, shipType.getSize());
	}

	@Override
	public String toString()
	{
		return shipType + " " + position.toString();
	}
}
