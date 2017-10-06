package de.queisler.battleship.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.queisler.battleship.data.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	User findByUsername(String username);
}
