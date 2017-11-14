package de.queisler.battleship;

import de.queisler.battleship.businessLogic.*;
import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.ShipType;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidShipException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ShipPlacementTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private Game game;

    @Before
    public void gameSetup() {
        game = new Game();
    }


    @Test
    public void addShipToFleet() throws InvalidShipException, InvalidPointException {
        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);

        assertTrue(player1.getFleet().containsShip(ship1));
    }

    @Test
    public void addShipWithInvalidStartPointShouldThrowException() throws InvalidShipException, InvalidPointException {
        expectedEx.expect(InvalidPointException.class);
        expectedEx.expectMessage("Der Punkt ist nicht gültig!");

        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(15, 1), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);
    }

    @Test
    public void addShipWithInvalidPositionShouldThrowException() throws InvalidShipException, InvalidPointException {
        expectedEx.expect(InvalidPointException.class);
        expectedEx.expectMessage("Diese Position beinhaltet ungültige Punkte!");

        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 9), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);
    }

    @Test()
    public void addSameShipTwiceToFleetShouldThrowException() throws InvalidShipException, InvalidPointException {
        expectedEx.expect(InvalidShipException.class);
        expectedEx.expectMessage("Ein Schiff desselben Typs ist bereits in der Flotte!");

        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);

        assertTrue(player1.getFleet().containsShip(ship1));

        player1.getFleet().addShip(ship1);
    }

    @Test()
    public void addTooManyShipsToFleetShouldThrowException() throws InvalidShipException, InvalidPointException {
        expectedEx.expect(InvalidShipException.class);
        expectedEx.expectMessage("Die Flotte ist bereits voll besetzt!");

        Player player1 = new Player("Max");
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
    public void addOverlappingShipsShouldThrowException() throws InvalidShipException, InvalidPointException {
        expectedEx.expect(InvalidShipException.class);
        expectedEx.expectMessage("Das Schiff überschneidet sich mit einem anderen in der Flotte!");

        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
        Ship ship2 = new Ship(ShipType.CARRIER, new Point(1, 3), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);

        assertTrue(player1.getFleet().containsShip(ship1));

        player1.getFleet().addShip(ship2);
    }

    @Test()
    public void addTouchingShipsShouldThrowException() throws InvalidShipException, InvalidPointException {
        expectedEx.expect(InvalidShipException.class);
        expectedEx.expectMessage("Das Schiff berührt ein anderes in der Flotte!");

        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);
        Ship ship2 = new Ship(ShipType.CARRIER, new Point(1, 5), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);

        assertTrue(player1.getFleet().containsShip(ship1));

        player1.getFleet().addShip(ship2);
    }

    @Test()
    public void removeShip() throws InvalidShipException, InvalidPointException {
        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);

        assertTrue(player1.getFleet().containsShip(ship1));

        player1.getFleet().removeShip(ship1);

        assertFalse(player1.getFleet().containsShip(ship1));
    }

    @Test()
    public void changePositionOfShip() throws InvalidShipException, InvalidPointException {
        Player player1 = new Player("Max");
        Ship ship1 = new Ship(ShipType.BATTLESHIP, new Point(1, 1), Alignment.HORIZONTAL);

        player1.getFleet().addShip(ship1);

        assertTrue(player1.getFleet().containsShip(ship1));
        assertTrue(ship1.getPosition().equals(new Position(new Point(1, 1), Alignment.HORIZONTAL, 4)));

        player1.getFleet().changeShipPostion(ship1, new Point(2, 1), Alignment.HORIZONTAL);

        assertTrue(ship1.getPosition().equals(new Position(new Point(2, 1), Alignment.HORIZONTAL, 4)));
    }

    @Test()
    public void checkIfFleetIsReady() throws InvalidShipException, InvalidPointException {
        Player player1 = new Player("Max");
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
