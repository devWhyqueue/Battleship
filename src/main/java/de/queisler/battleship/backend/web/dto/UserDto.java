package de.queisler.battleship.backend.web.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserDto
{
	@NotNull
	@NotEmpty
	private String username;

	@NotNull
	@NotEmpty
	private String password;
}
