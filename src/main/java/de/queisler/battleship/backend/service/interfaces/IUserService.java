package de.queisler.battleship.backend.service.interfaces;

import de.queisler.battleship.backend.web.dto.UserDto;
import de.queisler.battleship.backend.web.error.UserAlreadyExistException;
import de.queisler.battleship.data.model.User;

public interface IUserService
{
	User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;
}