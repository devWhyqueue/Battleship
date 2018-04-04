package de.queisler.battleship.businessLogic.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "username" }, callSuper = false)
@Entity
public class Player extends User
{
	private static final long serialVersionUID = 1L;

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
	@Transient
	private FieldMap hitMap;

	public Player(String username, String password)
	{
		super(username, password, true, true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		this.username = username;
		this.password = password;
	}

	public Player()
	{
		super("default", "password", true, true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		this.username = "default";
		this.password = "password";
	}
}