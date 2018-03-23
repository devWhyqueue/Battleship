package de.queisler.battleship.businessLogic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
	private Fleet fleet;
}