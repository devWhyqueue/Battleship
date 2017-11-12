package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private String name;
    private Fleet fleet;
    private Map map;

    public Player(String name) {
        this.name = name;
        this.fleet = new Fleet();
        this.map = new Map();
    }

    public AttackResult announceAttackResult(Point position) {
        return AttackResult.LOST;
    }

    public boolean isPlayerReady() {
        return true;
    }
}
