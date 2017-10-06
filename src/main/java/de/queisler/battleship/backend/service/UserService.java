package de.queisler.battleship.backend.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.queisler.battleship.backend.web.dto.UserDto;
import de.queisler.battleship.backend.web.error.UserAlreadyExistException;
import de.queisler.battleship.data.model.User;
import de.queisler.battleship.data.repositories.UserRepository;

@Service
public class UserService
{
	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException
	{

		if (usernameExist(accountDto.getUsername()))
		{
			throw new UserAlreadyExistException("There is an account with that Username!");
		}

		final User user = new User();

		user.setUsername(accountDto.getUsername());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setScore(0);
		user.setRoles(Arrays.asList("ROLE_USER"));

		return repository.save(user);
	}

	private boolean usernameExist(String username)
	{
		User user = repository.findByUsername(username);
		if (user != null)
		{
			return true;
		}
		return false;
	}
}
