package de.queisler.battleship.UI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.exceptions.PlayerAlreadyExistException;
import de.queisler.battleship.data.services.PlayerService;

@Controller
public class RegistrationController
{
	@Autowired
	PlayerService playerService;
	Player player = new Player();

	@GetMapping(value = "/register")
	public String register(Model model)
	{
		player = new Player();
		player.setUsername(null);
		player.setPassword(null);
		model.addAttribute(player);
		return "register";
	}

	@PostMapping(value = "/register")
	public String register(Model model, @ModelAttribute Player player)
	{
		try
		{
			this.player = player;
			playerService.savePlayer(player);
			return "redirect:login";
		}
		catch (TransactionSystemException e)
		{
			//TODO: JavaScript validation for IE
			model.addAttribute("error", "Fehler! Bitte beachten Sie die Mindestlängen der Felder.");
			return "register";
		}
		catch (PlayerAlreadyExistException e)
		{
			player.setUsername(null);
			model.addAttribute("error", e.getMessage());
			return "register";
		}
	}
}
