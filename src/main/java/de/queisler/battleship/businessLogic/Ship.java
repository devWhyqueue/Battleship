package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.ShipType;
import de.queisler.battleship.businessLogic.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"shipType"})
public class Ship {
    private ShipType shipType;
    private Position position;
    private Status status;

    public Ship(ShipType shipType, Position position) {
        this.shipType = shipType;
        this.position = position;
        this.status = Status.ALIVE;
    }

    public int getSize(){
        switch(shipType){
            case CARRIER:
                return 5;
            case BATTLESHIP:
                return 4;
            case CRUISER:
                return 3;
            case SUBMARINE:
                return 3;
            case DESTROYER:
                return 2;
            default:
                return 0; // FEHLER
        }
    }

    public boolean isAlive(){
        for(Point p : position.getPoints()) {
            if (!p.isHit()) return true;
        }
        return false;
    }
}
