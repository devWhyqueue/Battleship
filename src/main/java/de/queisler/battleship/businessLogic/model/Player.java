package de.queisler.battleship.businessLogic.model;

import de.queisler.battleship.businessLogic.enums.PlayerStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "username" })
public class Player
{
	private String username;
	private String password;
	private String vorname;
	private String nachname;
	private PlayerStatus status; // transient
	private Fleet fleet; // transient
}