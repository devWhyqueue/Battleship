package de.queisler.battleship.businessLogic.model;

import de.queisler.battleship.businessLogic.enums.PlayerStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode(of = { "username" })
@Entity
public class Player
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true)
	@NotNull
    @Size(min = 3)
	private String username;
	@NotNull
	@Size(min = 5)
	private String password;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@Transient
	private PlayerStatus status;
	@Transient
	private Fleet fleet;
}