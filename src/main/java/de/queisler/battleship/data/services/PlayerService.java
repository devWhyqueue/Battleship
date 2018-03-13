package de.queisler.battleship.data.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.exceptions.LoginException;
import de.queisler.battleship.data.exceptions.PlayerAlreadyExistException;

public interface PlayerService extends UserDetailsService
{
	Player savePlayer(Player player) throws PlayerAlreadyExistException;

	Player login(String username, String password) throws LoginException;
}