package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.exceptions.GameException;

import java.util.HashMap;

public class Game {
    private java.util.Map<Player, Map> players;

    public Game() {
        players = new HashMap<>();
    }

    public void addPlayer(Player player) throws GameException {
        if (players.size() < 2) players.put(player, new Map());
        else throw new GameException("Es sind bereits zwei Spieler im Spiel!");
    }

    public void removePlayer(Player player) throws GameException {
        if (players.containsKey(player)) {
            players.remove(player);
        } else throw new GameException("Dieser Spieler befindet sich nicht im Spiel!");
    }

    private boolean finished() {
        for (java.util.Map.Entry<Player, Map> entry : players.entrySet()) {
            if (!entry.getKey().getFleet().isAlive()) return true;
        }
        return false;
    }

    public AttackResult attack(Player attacker, Point point) throws GameException {
        for (java.util.Map.Entry<Player, Map> entry : players.entrySet()) {
            if (!entry.getKey().equals(attacker)) {
                if (!entry.getValue().getStatus(point)) {
                    entry.getValue().setStatus(point);
                    return entry.getKey().getFleet().attackFleet(point);
                }
                throw new GameException("Dieser Punkt wurde bereits attackiert!");
            }
        }
        return AttackResult.ERROR;
    }
}
