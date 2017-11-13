package de.queisler.battleship;

import de.queisler.battleship.businessLogic.Game;
import de.queisler.battleship.businessLogic.Player;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class GamePlayerManagementTest {
    private Game game;

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

        assertTrue(game.getPlayers().containsKey(player1));
        assertTrue(game.getPlayers().containsKey(player2));
    }

    @Test(expected = GameException.class)
    public void addTooManyPlayersShouldThrowException() throws GameException {
        Player player1 = new Player("Max");
        Player player2 = new Player("Moritz");
        Player player3 = new Player("Phillip");

        game.addPlayer(player1);
        game.addPlayer(player2);

        assertTrue(game.getPlayers().containsKey(player1));
        assertTrue(game.getPlayers().containsKey(player2));

        game.addPlayer(player3);
    }

    @Test(expected = GameException.class)
    public void addSamePlayerTwiceShouldThrowException() throws GameException {
        Player player1 = new Player("Max");
        Player player2 = new Player("Moritz");

        game.addPlayer(player1);
        game.addPlayer(player2);

        assertTrue(game.getPlayers().containsKey(player1));
        assertTrue(game.getPlayers().containsKey(player2));

        game.addPlayer(player2);
    }

    @Test()
    public void removePlayer() throws GameException {
        Player player1 = new Player("Max");
        Player player2 = new Player("Moritz");

        game.addPlayer(player1);
        game.addPlayer(player2);

        assertTrue(game.getPlayers().containsKey(player1));
        assertTrue(game.getPlayers().containsKey(player2));

        game.removePlayer(player1);

        assertFalse(game.getPlayers().containsKey(player1));
    }

    @Test(expected = GameException.class)
    public void removeNotExistingPlayerShouldThrowGameException() throws GameException {
        Player player1 = new Player("Max");

        assertFalse(game.getPlayers().containsKey(player1));

        game.removePlayer(player1);
    }
}
