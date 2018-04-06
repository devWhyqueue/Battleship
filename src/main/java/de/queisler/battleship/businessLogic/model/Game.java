package de.queisler.battleship.businessLogic.model;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.enums.PointStatus;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import lombok.Getter;

public class Game
{
	@Getter
	private Set<Player> players;
	@Getter
	private Player currentPlayer;
	@Getter
	private boolean active;

	private Instant lastActive;
	private GameActivationStatusThread activationStatusThread;
	private CurrentPlayerThread currentPlayerThread;

	public Game()
	{
		players = new HashSet<>();
		activationStatusThread = new GameActivationStatusThread();
		currentPlayerThread = new CurrentPlayerThread();

		updateLastActive();
		activationStatusThread.start();
	}

	public void addPlayer(Player player) throws GameException
	{
		if (players.size() == 2)
			throw new GameException("Es sind bereits zwei Spieler im Spiel!");
		if (players.contains(player))
			throw new GameException("Dieser Spieler befindet sich bereits im Spiel!");

		player.setHitMap(new FieldMap());
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

		if (currentPlayer == null)
		{
			currentPlayer = (Player) players.toArray()[ThreadLocalRandom.current().nextInt(0, 2)];
			currentPlayerThread.start();
		}
	}

	public Player getOpponent(Player player)
	{
		for (Player p : players)
		{
			if (!p.equals(player))
				return p;
		}
		return null;
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
			if (!attacker.equals(currentPlayer))
				throw new GameException("Der Gegner ist gerade am Zug!");

			Player opponent = getOpponent(attacker);
			if (attacker.getHitMap().getStatus(point) == PointStatus.UNKNOWN)
			{
				AttackResult result = opponent.getFleet().attack(point);
				if (result == AttackResult.MISS)
				{
					attacker.getHitMap().setStatus(point, PointStatus.WATER);
					updateCurrentPlayer(true);
				}
				else
				{
					attacker.getHitMap().setStatus(point, PointStatus.SHIP);
					updateCurrentPlayer(false);
				}

				updateLastActive();
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

	private void updateCurrentPlayer(boolean change)
	{
		currentPlayerThread.interrupt();
		if (change)
			currentPlayer = getOpponent(currentPlayer);
		currentPlayerThread = new CurrentPlayerThread();
		currentPlayerThread.start();
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

	private void updateLastActive()
	{
		active = true;
		lastActive = Instant.now();
	}

	private class GameActivationStatusThread extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
				Duration between = Duration.between(lastActive, Instant.now());
				if (between.toMinutes() > 5)
					active = false;
				try
				{
					Thread.sleep(TimeUnit.MINUTES.toMillis(3));
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	private class CurrentPlayerThread extends Thread
	{
		@Override
		public void run()
		{
			boolean running = false;
			do
			{
				try
				{
					Thread.sleep(TimeUnit.SECONDS.toMillis(30));

					currentPlayer = getOpponent(currentPlayer);
					running = true;
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
					running = false;
				}
			}
			while (running);
		}
	}

}
