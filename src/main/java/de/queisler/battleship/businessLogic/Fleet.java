package de.queisler.battleship.businessLogic;

import java.util.ArrayList;
import java.util.List;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.exceptions.FleetException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPositionException;

public class Fleet
{
	private List<Ship> ships;

	public Fleet()
	{
		this.ships = new ArrayList<>();
	}

	public void addShip(Ship ship) throws FleetException
	{
		validateShip(ship);
		ships.add(ship);
	}

	private void validateShip(Ship ship) throws FleetException
	{
		if (ships.size() > 4)
			throw new FleetException("Die Flotte ist bereits voll besetzt!");
		else if (ships.contains(ship))
			throw new FleetException("Ein Schiff desselben Typs ist bereits in der Flotte!");
		else
		{
			for (Ship s : ships)
			{
				if (s.getPosition().isOverlapping(ship.getPosition()))
					throw new FleetException("Das Schiff überschneidet sich mit einem anderen in der Flotte!");
				if (s.getPosition().isNextTo(ship.getPosition()))
					throw new FleetException("Das Schiff berührt ein anderes in der Flotte!");
			}
		}
	}

	public void removeShip(Ship ship) throws FleetException
	{
		int i = ships.indexOf(ship);
		if (i != -1)
		{
			ships.remove(ship);
		}
		else
		{
			throw new FleetException("Das angegebene Schiff befindet sich nicht in der Flotte!");
		}
	}

	public boolean containsShip(Ship ship)
	{
		return ships.contains(ship);
	}

	public void changeShipPostion(Ship ship, Point startPoint, Alignment alignment)
		throws FleetException, InvalidPositionException
	{
		int i = ships.indexOf(ship);
		if (i != -1)
		{
			ship.setPosition(new Position(startPoint, alignment, ship.getSize()));
		}
		else
		{
			throw new FleetException("Das angegebene Schiff befindet sich nicht in der Flotte!");
		}
	}

	public AttackResult getAttackResult(Point point)
	{
		for (Ship s : ships)
		{
			try
			{
				s.getPosition().markPointAsHit(point);

				if (!s.getPosition().allHit())
					return AttackResult.HIT;
				else if (s.getPosition().allHit() && isAlive())
					return AttackResult.SUNK;
				else
					return AttackResult.LOST;
			}
			catch (InvalidPointException e)
			{
				// Schiff ist nicht an diesem Punkt
			}
		}
		return AttackResult.MISS;
	}

	public boolean isReady()
	{
		return ships.size() == 5;
	}

	public boolean isAlive()
	{
		for (Ship s : ships)
		{
			if (!s.getPosition().allHit())
				return true;
		}
		return false;
	}
}
