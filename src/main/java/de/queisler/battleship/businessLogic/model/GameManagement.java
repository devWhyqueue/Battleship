package de.queisler.battleship.businessLogic.model;

import de.queisler.battleship.businessLogic.exceptions.GameException;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameManagement {
    private List<Game> games;
    private GameCleanUpThread cleanUpThread;

    public GameManagement() {
        games = Collections.synchronizedList(new ArrayList<Game>());
        cleanUpThread = new GameCleanUpThread();

        cleanUpThread.start();
    }

    public void addGame(Game game) throws GameException {
        ListIterator<Game> iter = games.listIterator();

        while (iter.hasNext()) {
            Game g = iter.next();
            for (Player p : game.getPlayers())
                if (g.containsPlayer(p)) throw new GameException("A game with one of these players already exists!");
        }
        iter.add(game);
    }

    public Game getGame(Player player) throws GameException {
        ListIterator<Game> iter = games.listIterator();

        while (iter.hasNext()) {
            Game g = iter.next();
            if (g.containsPlayer(player)) return g;
        }
        throw new GameException("This player has no active games!");
    }

    private class GameCleanUpThread extends Thread {
        @Override
        public void run() {
            while (true) {
                ListIterator<Game> iter = games.listIterator();
                while (iter.hasNext()) {
                    Game g = iter.next();
                    if (!g.isActive()) iter.remove();
                }
                try {
                    Thread.sleep(TimeUnit.MINUTES.toMillis(2));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
