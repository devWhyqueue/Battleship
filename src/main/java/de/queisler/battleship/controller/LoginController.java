package de.queisler.battleship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController
{

	// Login form
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}

	// Login form with error
	@PostMapping("/login")
	public String login(@RequestParam(value = "username") String username,
		@RequestParam(value = "password") String password)
	{
		System.out.println(username);
		System.out.println(password);
		// do authentication
		return "redirect:welcome";
	}
}