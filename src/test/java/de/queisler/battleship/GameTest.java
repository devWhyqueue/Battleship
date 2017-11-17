package de.queisler.battleship;

import static junit.framework.TestCase.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.queisler.battleship.businessLogic.Game;
import de.queisler.battleship.businessLogic.Player;
import de.queisler.battleship.businessLogic.Point;
import de.queisler.battleship.businessLogic.Ship;
import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.enums.ShipType;
import de.queisler.battleship.businessLogic.exceptions.FleetException;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPositionException;

public class GameTest
{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	private Game game;
	private Player player1;
	private Player player2;

	@Before
	public void gameSetup() throws InvalidPositionException, InvalidPointException, FleetException, GameException
	{
		game = new Game();

		Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
		Ship ship2 = new Ship(ShipType.CARRIER, new Point(3, 1), Alignment.HORIZONTAL);
		Ship ship3 = new Ship(ShipType.CRUISER, new Point(5, 1), Alignment.HORIZONTAL);
		Ship ship4 = new Ship(ShipType.DESTROYER, new Point(7, 1), Alignment.HORIZONTAL);
		Ship ship5 = new Ship(ShipType.SUBMARINE, new Point(9, 1), Alignment.HORIZONTAL);

		Ship ship1a = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
		Ship ship2a = new Ship(ShipType.CARRIER, new Point(3, 1), Alignment.HORIZONTAL);
		Ship ship3a = new Ship(ShipType.CRUISER, new Point(5, 1), Alignment.HORIZONTAL);
		Ship ship4a = new Ship(ShipType.DESTROYER, new Point(7, 1), Alignment.HORIZONTAL);
		Ship ship5a = new Ship(ShipType.SUBMARINE, new Point(9, 1), Alignment.HORIZONTAL);

		player1 = new Player("Max");
		player1.getFleet().addShip(ship1);
		player1.getFleet().addShip(ship2);
		player1.getFleet().addShip(ship3);
		player1.getFleet().addShip(ship4);
		player1.getFleet().addShip(ship5);

		player2 = new Player("Moritz");
		player2.getFleet().addShip(ship1a);
		player2.getFleet().addShip(ship2a);
		player2.getFleet().addShip(ship3a);
		player2.getFleet().addShip(ship4a);
		player2.getFleet().addShip(ship5a);

		game.addPlayer(player1);
		game.addPlayer(player2);
	}

	@Test
	public void checkIfGameIsReady()
	{
		assertTrue(game.isReady());
	}

	@Test
	public void attackAndMiss() throws GameException, InvalidPointException
	{
		assertTrue(game.attack(player1, new Point(9, 9)).equals(AttackResult.MISS));
	}

	@Test
	public void attackAndHit() throws GameException, InvalidPointException
	{
		assertTrue(game.attack(player1, new Point(1, 2)).equals(AttackResult.HIT));
	}

	@Test
	public void attackAndSunk() throws GameException, InvalidPointException
	{
		assertTrue(game.attack(player1, new Point(7, 1)).equals(AttackResult.HIT));
		assertTrue(game.attack(player1, new Point(7, 2)).equals(AttackResult.SUNK));
	}

	@Test
	public void attackAndLost() throws GameException, InvalidPointException
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				if (!(i == 1 && j == 1))
					game.attack(player1, new Point(i, j));
			}
		}
		assertTrue(game.getWinner() == null);
		assertTrue(game.attack(player1, new Point(1, 1)).equals(AttackResult.LOST));
		assertTrue(game.getWinner().equals(player1));
	}

	@Test
	public void attackSamePointTwiceShouldThrowException() throws GameException, InvalidPointException
	{
		expectedEx.expect(GameException.class);
		expectedEx.expectMessage("Dieser Punkt wurde bereits attackiert!");

		game.attack(player1, new Point(1, 1));
		game.attack(player1, new Point(1, 1));
	}

	@Test
	public void attackAlthoughGameIsFinishedShouldThrowException() throws GameException, InvalidPointException
	{
		expectedEx.expect(GameException.class);
		expectedEx.expectMessage("Das Spiel ist bereits zuende!");

		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				if (!(i == 1 && j == 1) || !(i == 10 && j == 10))
					game.attack(player1, new Point(i, j));
			}
		}
		assertTrue(game.attack(player1, new Point(1, 1)).equals(AttackResult.LOST));
		assertTrue(game.getWinner().equals(player1));

		game.attack(player1, new Point(10, 10));
	}

}
