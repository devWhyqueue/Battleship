package de.queisler.battleship.UI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.queisler.battleship.data.exceptions.LoginException;
import de.queisler.battleship.data.services.PlayerService;

@Controller
public class LoginController
{
	@Autowired
	private PlayerService playerService;
	private String errorMsg;

	// Login
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}

	// Login form
	@PostMapping("/login")
	public String login(@RequestParam(value = "username") String username,
		@RequestParam(value = "password") String password)
	{
		try
		{
			playerService.login(username, password);
			return "redirect:lobby";
		}
		catch (LoginException e)
		{
			errorMsg = e.getMessage();
			return "login-error";
		}
	}

	// Login error
	@RequestMapping("/login-error")
	public String loginError(Model model)
	{
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", errorMsg);
		return "login";
	}
}
