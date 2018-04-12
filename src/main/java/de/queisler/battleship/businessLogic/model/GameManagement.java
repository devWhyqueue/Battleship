package de.queisler.battleship.businessLogic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.queisler.battleship.businessLogic.exceptions.GameException;

public class GameManagement
{
	private List<Game> games;
	private GameCleanUpThread cleanUpThread;

	public GameManagement()
	{
		games = Collections.synchronizedList(new ArrayList<Game>());
		cleanUpThread = new GameCleanUpThread();

		cleanUpThread.start();
	}

	public void addGame(Game game) throws GameException
	{
		for (Game g : games)
			for (Player p : game.getPlayers())
				if (g.containsPlayer(p))
					throw new GameException("A game with one of these players already exists!");
		games.add(game);
	}

	public void removeGame(Player player) throws GameException
	{
		for (Game g : games)
			if (g.containsPlayer(player))
			{
				games.remove(g);
				return;
			}
		throw new GameException("This player has no active games!");
	}

	public Game getGame(Player player) throws GameException
	{
		for (Game g : games)
			if (g.containsPlayer(player))
				return g;
		throw new GameException("This player has no active games!");
	}

	private class GameCleanUpThread extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
				for (Game g : games)
				{
					if (!g.isActive())
						games.remove(g);
				}
				try
				{
					Thread.sleep(TimeUnit.MINUTES.toMillis(2));
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
