package de.queisler.battleship;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.ShipType;
import de.queisler.battleship.businessLogic.exceptions.FleetException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPositionException;
import de.queisler.battleship.businessLogic.model.Fleet;
import de.queisler.battleship.businessLogic.model.Game;
import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.businessLogic.model.Point;
import de.queisler.battleship.businessLogic.model.Position;
import de.queisler.battleship.businessLogic.model.Ship;

public class ShipPlacementTest
{

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	private Game game;
	private Player player1;

	@Before
	public void gameSetup()
	{
		game = new Game();
		player1 = new Player();
		player1.setUsername("Max");
		player1.setFleet(new Fleet());
	}

	@Test
	public void addShipToFleet() throws FleetException, InvalidPositionException, InvalidPointException
	{
		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 7), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);

		assertTrue(player1.getFleet().containsShip(ship1));
	}

	@Test
	public void addShipWithInvalidStartPointShouldThrowException()
		throws FleetException, InvalidPositionException, InvalidPointException
	{
		expectedEx.expect(InvalidPointException.class);
		expectedEx.expectMessage("Der Punkt ist nicht gültig!");

		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(15, 1), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);
	}

	@Test
	public void addShipWithInvalidPositionShouldThrowException()
		throws FleetException, InvalidPointException, InvalidPositionException
	{
		expectedEx.expect(InvalidPositionException.class);
		expectedEx.expectMessage("Diese Position beinhaltet ungültige Punkte!");

		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 9), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);
	}

	@Test()
	public void addSameShipTwiceToFleetShouldThrowException()
		throws FleetException, InvalidPointException, InvalidPositionException
	{
		expectedEx.expect(FleetException.class);
		expectedEx.expectMessage("Ein Schiff desselben Typs ist bereits in der Flotte!");

		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);

		assertTrue(player1.getFleet().containsShip(ship1));

		player1.getFleet().addShip(ship1);
	}

	@Test()
	public void addTooManyShipsToFleetShouldThrowException()
		throws FleetException, InvalidPointException, InvalidPositionException
	{
		expectedEx.expect(FleetException.class);
		expectedEx.expectMessage("Die Flotte ist bereits voll besetzt!");

		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
		Ship ship2 = new Ship(ShipType.CARRIER, new Point(3, 1), Alignment.HORIZONTAL);
		Ship ship3 = new Ship(ShipType.CRUISER, new Point(5, 1), Alignment.HORIZONTAL);
		Ship ship4 = new Ship(ShipType.DESTROYER, new Point(7, 1), Alignment.HORIZONTAL);
		Ship ship5 = new Ship(ShipType.SUBMARINE, new Point(9, 1), Alignment.HORIZONTAL);
		Ship ship6 = new Ship(ShipType.BATTLESHIP, new Point(1, 9), Alignment.VERTICAL);

		player1.getFleet().addShip(ship1);
		player1.getFleet().addShip(ship2);
		player1.getFleet().addShip(ship3);
		player1.getFleet().addShip(ship4);
		player1.getFleet().addShip(ship5);

		assertTrue(player1.getFleet().containsShip(ship1));
		assertTrue(player1.getFleet().containsShip(ship2));
		assertTrue(player1.getFleet().containsShip(ship3));
		assertTrue(player1.getFleet().containsShip(ship4));
		assertTrue(player1.getFleet().containsShip(ship5));

		player1.getFleet().addShip(ship6);
	}

	@Test()
	public void addOverlappingShipsShouldThrowException()
		throws FleetException, InvalidPointException, InvalidPositionException
	{
		expectedEx.expect(FleetException.class);
		expectedEx.expectMessage("Das Schiff überschneidet sich mit einem anderen in der Flotte!");

		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
		Ship ship2 = new Ship(ShipType.CARRIER, new Point(1, 3), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);

		assertTrue(player1.getFleet().containsShip(ship1));

		player1.getFleet().addShip(ship2);
	}

	@Test()
	public void addTouchingShipsShouldThrowException()
		throws FleetException, InvalidPointException, InvalidPositionException
	{
		expectedEx.expect(FleetException.class);
		expectedEx.expectMessage("Das Schiff berührt ein anderes in der Flotte!");

		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
		Ship ship2 = new Ship(ShipType.CARRIER, new Point(1, 5), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);

		assertTrue(player1.getFleet().containsShip(ship1));

		player1.getFleet().addShip(ship2);
	}

	@Test()
	public void removeShip() throws FleetException, InvalidPointException, InvalidPositionException
	{
		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);

		assertTrue(player1.getFleet().containsShip(ship1));

		player1.getFleet().removeShip(ship1);

		assertFalse(player1.getFleet().containsShip(ship1));
	}

	@Test()
	public void changePositionOfShip() throws FleetException, InvalidPointException, InvalidPositionException
	{
		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);

		assertTrue(player1.getFleet().containsShip(ship1));
		assertTrue(ship1.getPosition().equals(new Position(new Point(1, 1), Alignment.HORIZONTAL, 4)));

		player1.getFleet().changeShipPostion(ship1, new Point(2, 1), Alignment.HORIZONTAL);

		assertTrue(ship1.getPosition().equals(new Position(new Point(2, 1), Alignment.HORIZONTAL, 4)));
	}

	@Test()
	public void checkIfFleetIsReady() throws FleetException, InvalidPointException, InvalidPositionException
	{
		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
		Ship ship2 = new Ship(ShipType.CARRIER, new Point(3, 1), Alignment.HORIZONTAL);
		Ship ship3 = new Ship(ShipType.CRUISER, new Point(5, 1), Alignment.HORIZONTAL);
		Ship ship4 = new Ship(ShipType.DESTROYER, new Point(7, 1), Alignment.HORIZONTAL);
		Ship ship5 = new Ship(ShipType.SUBMARINE, new Point(9, 1), Alignment.HORIZONTAL);

		player1.getFleet().addShip(ship1);
		player1.getFleet().addShip(ship2);
		player1.getFleet().addShip(ship3);
		player1.getFleet().addShip(ship4);
		player1.getFleet().addShip(ship5);

		assertTrue(player1.getFleet().containsShip(ship1));
		assertTrue(player1.getFleet().containsShip(ship2));
		assertTrue(player1.getFleet().containsShip(ship3));
		assertTrue(player1.getFleet().containsShip(ship4));
		assertTrue(player1.getFleet().containsShip(ship5));

		assertTrue(player1.getFleet().isReady());
	}
}
