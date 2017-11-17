package de.queisler.battleship;

import de.queisler.battleship.businessLogic.exceptions.GameException;
import de.queisler.battleship.businessLogic.model.Game;
import de.queisler.battleship.businessLogic.model.Player;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PlayerManagementTest {
    private Game game;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void gameSetup() {
        game = new Game();
    }

    @Test()
    public void addPlayers() throws GameException {
        Player player1 = new Player("Max");
        Player player2 = new Player("Moritz");

        game.addPlayer(player1);
        game.addPlayer(player2);

        assertTrue(game.containsPlayer(player1));
        assertTrue(game.containsPlayer(player2));
    }

    @Test
    public void addTooManyPlayersShouldThrowException() throws GameException {
        expectedEx.expect(GameException.class);
        expectedEx.expectMessage("Es sind bereits zwei Spieler im Spiel!");

        Player player1 = new Player("Max");
        Player player2 = new Player("Moritz");

        game.addPlayer(player1);
        game.addPlayer(player2);

        assertTrue(game.containsPlayer(player1));
        assertTrue(game.containsPlayer(player2));

        game.addPlayer(player2);
    }

    @Test
    public void addSamePlayerTwiceShouldThrowException() throws GameException {
        expectedEx.expect(GameException.class);
        expectedEx.expectMessage("Dieser Spieler befindet sich bereits im Spiel!");

        Player player1 = new Player("Max");

        game.addPlayer(player1);

        assertTrue(game.containsPlayer(player1));

        game.addPlayer(player1);
    }

    @Test()
    public void removePlayer() throws GameException {
        Player player1 = new Player("Max");
        Player player2 = new Player("Moritz");

        game.addPlayer(player1);
        game.addPlayer(player2);

        assertTrue(game.containsPlayer(player1));
        assertTrue(game.containsPlayer(player2));

        game.removePlayer(player1);

        assertFalse(game.containsPlayer(player1));
    }

    @Test
    public void removeNotExistingPlayerShouldThrowGameException() throws GameException {
        expectedEx.expect(GameException.class);
        expectedEx.expectMessage("Dieser Spieler befindet sich nicht im Spiel!");

        Player player1 = new Player("Max");

        assertFalse(game.containsPlayer(player1));

        game.removePlayer(player1);
    }
}
