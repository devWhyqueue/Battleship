package de.queisler.battleship.businessLogic;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"name"})
public class Player {
    private String name;
    private Fleet fleet;

    public Player(String name) {
        this.name = name;
        this.fleet = new Fleet();
    }
    }