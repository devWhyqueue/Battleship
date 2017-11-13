package de.queisler.battleship.businessLogic;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class Game {
    private java.util.Map<Player, Map> players;

    public Game() {
        players = new HashMap<>();
    }

    public void addPlayer(Player player) throws GameException {
        if (players.containsKey(player)) throw new GameException("Dieser Spieler befindet sich bereits im Spiel!");
        else if (players.size() == 2) throw new GameException("Es sind bereits zwei Spieler im Spiel!");
        else players.put(player, new Map());
    }

    public void removePlayer(Player player) throws GameException {
        if (players.containsKey(player)) {
            players.remove(player);
        } else throw new GameException("Dieser Spieler befindet sich nicht im Spiel!");
    }

    public boolean isReady() {
        boolean ready = players.size() == 2;
        for (java.util.Map.Entry<Player, Map> entry : players.entrySet()) {
            if (!entry.getKey().getFleet().isReady()) ready = false;
        }
        return ready;
    }

    public AttackResult attack(Player attacker, Point point) throws GameException {
        for (java.util.Map.Entry<Player, Map> entry : players.entrySet()) {
            if (!entry.getKey().equals(attacker)) {
                if (!entry.getValue().getStatus(point)) {
                    entry.getValue().setStatus(point);
                    return entry.getKey().announceAttackResult(point);
                }
                throw new GameException("Dieser Punkt wurde bereits attackiert!");
            }
        }
        throw new GameException("Ein unbekannter Fehler ist aufgetreten!");
    }

    public Player getWinner() {
        for (java.util.Map.Entry<Player, Map> entry : players.entrySet()) {
            if (!entry.getKey().getFleet().isAlive()) return entry.getKey();
        }
        return null;
    }
}
