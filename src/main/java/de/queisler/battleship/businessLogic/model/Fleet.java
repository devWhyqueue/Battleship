package de.queisler.battleship.businessLogic.model;

import java.util.ArrayList;
import java.util.List;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.enums.PointStatus;
import de.queisler.battleship.businessLogic.enums.ShipType;
import de.queisler.battleship.businessLogic.exceptions.FleetException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPositionException;
import lombok.Getter;

@Getter
public class Fleet
{
	private List<Ship> ships;
	private FieldMap shipMap;

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
					throw new FleetException(
						"Das Schiff " + s.toString() + " überschneidet sich mit einem anderen in der Flotte!");
				if (s.getPosition().isNextTo(ship.getPosition()))
					throw new FleetException("Das Schiff " + s.toString() + " berührt ein anderes in der Flotte!");
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

	public Ship getShip(ShipType shipType)
	{
		for (Ship s : ships)
		{
			if (s.getShipType() == shipType)
				return s;
		}
		return null;
	}

	public void changeShipPostion(Ship ship, Point startPoint, Alignment alignment)
		throws FleetException, InvalidPositionException
	{
		int i = ships.indexOf(ship);
		if (i != -1)
		{
			ship.setPosition(new Position(startPoint, alignment, ship.getShipType().getSize()));
		}
		else
		{
			throw new FleetException("Das angegebene Schiff befindet sich nicht in der Flotte!");
		}
	}

	public FieldMap getShipMap()
	{
		shipMap = new FieldMap();
		for (Ship s : ships)
		{
			List<Point> pts = s.getPosition().getPoints();
			for (Point pt : pts)
				shipMap.setStatus(pt, PointStatus.getByShipType(s.getShipType()));
		}
		return shipMap;
	}

	public AttackResult attack(Point point)
	{
		for (Ship s : ships)
		{
			try
			{
				s.getPosition().markPointAsHit(point);

				if (!s.getPosition().allHit())
					return AttackResult.HIT;
				else if (s.getPosition().allHit() && isAlive())
					return AttackResult.build(s.getShipType());
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
