package de.queisler.battleship.businessLogic.model;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.enums.PointStatus;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import lombok.Getter;

public class Game extends Thread
{
	@Getter
	private Set<Player> players;
	@Getter
	private boolean active;
	private Instant lastActive;

	public Game()
	{
		players = new HashSet<>();

		active = true;
		lastActive = Instant.now();
		this.start();
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
		lastActive = Instant.now();

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

	@Override
	public void run()
	{
		while (true)
		{
			Duration between = Duration.between(lastActive, Instant.now());
			if (between.toMinutes() > 5)
				this.active = false;
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
}
