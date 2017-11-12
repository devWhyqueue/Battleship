package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidShipException;
import javafx.geometry.Pos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Fleet {
    private Set<Ship> ships;

    public Fleet() {
        this.ships = new HashSet<>();
    }

    public void addShip(Ship ship) throws InvalidShipException {
        validateShip(ship);
        ships.add(ship);
    }

    private void validateShip(Ship ship) throws InvalidShipException {
        if (ships.contains(ship))
            throw new InvalidShipException("Ein Schiff desselben Typs ist bereits in der Flotte!");
        else if (ships.size() > 5) throw new InvalidShipException("Die Flotte ist bereits voll besetzt!");
        else {
            for (Ship s : ships) {
                if (s.getPosition().isOverlapping(ship.getPosition()))
                    throw new InvalidShipException("Das Schiff überschneidet sich mit einem anderen in der Flotte!");
                if(s.getPosition().isNextTo(ship.getPosition()))
                    throw new InvalidShipException("Das Schiff berührt ein anderes in der Flotte!");
            }
        }
    }

    public boolean isFleetReady(){
        return ships.size() == 5;
    }

    public AttackResult attackFleet(Point point){
        for(Ship s : ships){
           if(s.getPosition().getPoints().contains(point)){
                s.getPosition().getPoints().indexOf(point)
           }

        }
    }
}
