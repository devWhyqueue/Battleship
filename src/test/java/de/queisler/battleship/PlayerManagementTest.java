package de.queisler.battleship;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.queisler.battleship.businessLogic.exceptions.GameException;
import de.queisler.battleship.businessLogic.model.Fleet;
import de.queisler.battleship.businessLogic.model.Game;
import de.queisler.battleship.businessLogic.model.Player;

public class PlayerManagementTest
{
	private Game game;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void gameSetup()
	{
		game = new Game();
	}

	@Test()
	public void addPlayers() throws GameException
	{
		Player player1 = new Player();
		player1.setUsername("Max");
		player1.setFleet(new Fleet());
		Player player2 = new Player();
		player2.setUsername("Moritz");
		player2.setFleet(new Fleet());

		game.addPlayer(player1);
		game.addPlayer(player2);

		assertTrue(game.containsPlayer(player1));
		assertTrue(game.containsPlayer(player2));
	}

	@Test
	public void addTooManyPlayersShouldThrowException() throws GameException
	{
		expectedEx.expect(GameException.class);
		expectedEx.expectMessage("Es sind bereits zwei Spieler im Spiel!");

		Player player1 = new Player();
		player1.setUsername("Max");
		player1.setFleet(new Fleet());
		Player player2 = new Player();
		player2.setUsername("Moritz");
		player2.setFleet(new Fleet());

		game.addPlayer(player1);
		game.addPlayer(player2);

		assertTrue(game.containsPlayer(player1));
		assertTrue(game.containsPlayer(player2));

		game.addPlayer(player2);
	}

	@Test
	public void addSamePlayerTwiceShouldThrowException() throws GameException
	{
		expectedEx.expect(GameException.class);
		expectedEx.expectMessage("Dieser Spieler befindet sich bereits im Spiel!");

		Player player1 = new Player();
		player1.setUsername("Max");
		player1.setFleet(new Fleet());

		game.addPlayer(player1);

		assertTrue(game.containsPlayer(player1));

		game.addPlayer(player1);
	}

	@Test()
	public void removePlayer() throws GameException
	{
		Player player1 = new Player();
		player1.setUsername("Max");
		player1.setFleet(new Fleet());
		Player player2 = new Player();
		player2.setUsername("Moritz");
		player2.setFleet(new Fleet());

		game.addPlayer(player1);
		game.addPlayer(player2);

		assertTrue(game.containsPlayer(player1));
		assertTrue(game.containsPlayer(player2));

		game.removePlayer(player1);

		assertFalse(game.containsPlayer(player1));
	}

	@Test
	public void removeNotExistingPlayerShouldThrowGameException() throws GameException
	{
		expectedEx.expect(GameException.class);
		expectedEx.expectMessage("Dieser Spieler befindet sich nicht im Spiel!");

		Player player1 = new Player();
		player1.setUsername("Max");
		player1.setFleet(new Fleet());

		assertFalse(game.containsPlayer(player1));

		game.removePlayer(player1);
	}
}
