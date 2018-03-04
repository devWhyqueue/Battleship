package de.queisler.battleship.businessLogic.model;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.enums.PointStatus;
import de.queisler.battleship.businessLogic.exceptions.GameException;

import java.util.HashMap;

public class Game
{
	private java.util.Map<Player, HitMap> players;

	public Game()
	{
		players = new HashMap<>();
	}

	public void addPlayer(Player player) throws GameException
	{
		if (players.size() == 2)
			throw new GameException("Es sind bereits zwei Spieler im Spiel!");
		else if (players.containsKey(player))
			throw new GameException("Dieser Spieler befindet sich bereits im Spiel!");
		else
			players.put(player, new HitMap());
	}

	public void removePlayer(Player player) throws GameException
	{
		if (players.containsKey(player))
		{
			players.remove(player);
		}
		else
			throw new GameException("Dieser Spieler befindet sich nicht im Spiel!");
	}

	public boolean containsPlayer(Player player)
	{
		return players.containsKey(player);
	}

	public boolean isReady()
	{
		boolean ready = players.size() == 2;
		for (java.util.Map.Entry<Player, HitMap> entry : players.entrySet())
		{
			if (!entry.getKey().getFleet().isReady())
				ready = false;
		}
		return ready;
	}

	public AttackResult attack(Player attacker, Point point) throws GameException
	{
		if (isReady() && determineWinner() == null)
		{
			for (java.util.Map.Entry<Player, HitMap> entry : players.entrySet())
			{
				if (!entry.getKey().equals(attacker))
				{
					if (entry.getValue().getStatus(point) == PointStatus.UNKNOWN)
					{
						AttackResult result = entry.getKey().getFleet().attack(point);
						if (result == AttackResult.MISS) entry.getValue().setStatus(point, PointStatus.WATER);
						else entry.getValue().setStatus(point, PointStatus.SHIP);

						return result;
					}
					throw new GameException("Dieser Punkt wurde bereits attackiert!");
				}
			}
		}
		else
		{
			if (determineWinner() == null)
				throw new GameException("Die Spieler sind noch nicht bereit, um zu attackieren!");
			else
				throw new GameException("Das Spiel ist bereits zuende!");
		}
		throw new GameException("Ein unbekannter Fehler ist aufgetreten!");
	}

	public Player determineWinner()
	{
		Player winner = null;
		for (java.util.Map.Entry<Player, HitMap> entry : players.entrySet())
		{
			if (entry.getKey().getFleet().isAlive())
			{
				if (winner == null)
					winner = entry.getKey();
				else
					return null;
			}
		}
		return winner;
	}
}
