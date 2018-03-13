package de.queisler.battleship.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.queisler.battleship.businessLogic.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>
{
	Player findByUsername(String username);
}
