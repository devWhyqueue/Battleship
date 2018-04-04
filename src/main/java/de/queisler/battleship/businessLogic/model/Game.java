package de.queisler.battleship.businessLogic.model;

import java.util.HashSet;
import java.util.Set;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.enums.PointStatus;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import lombok.Getter;

@Getter
public class Game
{
	private Set<Player> players;
	private boolean active;

	public Game()
	{
		players = new HashSet<>();
	}

	public void addPlayer(Player player) throws GameException
	{
		if (players.size() == 2)
			throw new GameException("Es sind bereits zwei Spieler im Spiel!");
		if (players.contains(player))
			throw new GameException("Dieser Spieler befindet sich bereits im Spiel!");

		players.add(player);
	}

	public void removePlayer(Player player) throws GameException
	{
		if (!players.contains(player))
			throw new GameException("Dieser Spieler befindet sich nicht im Spiel!");

		players.remove(player);
	}

	public boolean containsPlayer(Player player)
	{
		return players.contains(player);
	}

	public void startGame() throws GameException
	{
		if (!isReady())
			throw new GameException("Spieler sind noch nicht bereit!");
		if (active)
			throw new GameException("Spiel läuft bereits!");

		for (Player p : players)
			p.setHitMap(new FieldMap());

		active = true;
	}

	public boolean isReady()
	{
		boolean ready = players.size() == 2;
		for (Player p : players)
		{
			if (p.getFleet() == null || !p.getFleet().isReady())
				ready = false;
		}
		return ready;
	}

	public AttackResult attack(Player attacker, Point point) throws GameException
	{
		if (isReady() && determineWinner() == null)
		{
			Player opponent = null;
			for (Player p : players)
			{
				if (!p.equals(attacker))
					opponent = p;
			}

			if (attacker.getHitMap().getStatus(point) == PointStatus.UNKNOWN)
			{
				AttackResult result = opponent.getFleet().attack(point);
				if (result == AttackResult.MISS)
					attacker.getHitMap().setStatus(point, PointStatus.WATER);
				else
					attacker.getHitMap().setStatus(point, PointStatus.SHIP);

				return result;
			}
			throw new GameException("Dieser Punkt wurde bereits attackiert!");
		}
		else
		{
			if (determineWinner() == null)
				throw new GameException("Die Spieler sind noch nicht bereit, um zu attackieren!");
			else
				throw new GameException("Das Spiel ist bereits zuende!");
		}
	}

	public Player determineWinner()
	{
		Player winner = null;
		for (Player p : players)
		{
			if (p.getFleet().isAlive())
			{
				if (winner == null)
					winner = p;
				else
					return null;
			}
		}
		active = false;
		return winner;
	}
}
