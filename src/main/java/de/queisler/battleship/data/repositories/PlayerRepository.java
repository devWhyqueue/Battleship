package de.queisler.battleship.data.repositories;

import de.queisler.battleship.businessLogic.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long>
{
	Player findByUsername(String username);
}
