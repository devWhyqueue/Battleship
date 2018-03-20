package de.queisler.battleship.data.services;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.exceptions.PlayerAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PlayerService extends UserDetailsService
{
	Player savePlayer(Player player) throws PlayerAlreadyExistException;

	List<Player> getAllPlayers();
}