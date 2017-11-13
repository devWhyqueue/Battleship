package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.exceptions.InvalidShipException;

import java.util.ArrayList;
import java.util.List;

public class Fleet {
    private List<Ship> ships;

    public Fleet() {
        this.ships = new ArrayList<>();
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

    public void changeShipPostion(Ship ship, Position newPosition) throws InvalidShipException {
        int i = ships.indexOf(ship);
        if (i != -1) {
            ship.setPosition(newPosition);
        } else {
            throw new InvalidShipException("Das angegebene Schiff befindet sich nicht in der Flotte!");
        }
    }

    public void removeShip(Ship ship) throws InvalidShipException {
        int i = ships.indexOf(ship);
        if (i != -1) {
            ships.remove(ship);
        } else {
            throw new InvalidShipException("Das angegebene Schiff befindet sich nicht in der Flotte!");
        }
    }

    public boolean isReady() {
        return ships.size() == 5;
    }

    public AttackResult attackFleet(Point point){
        for(Ship s : ships){
            int i = s.getPosition().getPoints().indexOf(point);
            if (i != -1) {
                s.getPosition().getPoints().get(i).setHit(true);

                if (s.isAlive()) return AttackResult.HIT;
                else if (!s.isAlive() && isAlive()) return AttackResult.SUNK;
                else return AttackResult.LOST;
            }
        }
        return AttackResult.MISS;
    }

    public boolean isAlive() {
        for (Ship s : ships) {
            if (s.isAlive()) return true;
        }
        return false;
    }
}
