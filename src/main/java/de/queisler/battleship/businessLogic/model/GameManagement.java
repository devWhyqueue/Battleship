package de.queisler.battleship.businessLogic.model;

import java.util.ArrayList;
import java.util.List;

import de.queisler.battleship.businessLogic.exceptions.GameException;

public class GameManagement
{
	private List<Game> games;

	public GameManagement()
	{
		this.games = new ArrayList<>();
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
}
