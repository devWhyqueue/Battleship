package de.queisler.battleship.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.queisler.battleship.data.model.User;
import de.queisler.battleship.data.repositories.UserRepository;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;

	// 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{

		User user = userRepository.findByUsername(username);
		if (user == null)
		{
			throw new UsernameNotFoundException("No user found with username: " + username);
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
			user.getPassword().toLowerCase(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
			getAuthorities(user.getRoles()));
	}

	private static List<GrantedAuthority> getAuthorities(List<String> roles)
	{
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles)
		{
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}
