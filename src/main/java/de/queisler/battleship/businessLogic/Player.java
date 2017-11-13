package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import lombok.Getter;

@Getter
public class Player {
    private String name;
    private Fleet fleet;

    public Player(String name) {
        this.name = name;
        this.fleet = new Fleet();
    }

    public AttackResult announceAttackResult(Point point) {
        return getFleet().attackFleet(point);
    }
    }