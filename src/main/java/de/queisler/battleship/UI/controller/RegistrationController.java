package de.queisler.battleship.UI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.queisler.battleship.businessLogic.model.Player;

@Controller
public class RegistrationController
{
	@GetMapping(value = "/register")
	public String register(Model model)
	{
		model.addAttribute(new Player());
		return "register";
	}
}
