package de.queisler.battleship.UI.controller;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.exceptions.PlayerAlreadyExistException;
import de.queisler.battleship.data.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController
{
    @Autowired
    PlayerService playerService;
    Player player = new Player();

	@GetMapping(value = "/register")
	public String register(Model model)
	{
		model.addAttribute(new Player());
		return "register";
	}

    @GetMapping(value = "/register", params = "error")
    public String registerError(Model model) {
        player.setUsername(null);
        model.addAttribute(player);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute Player player) {
        try {
            this.player = player;
            playerService.savePlayer(player);
            return "redirect:login";
        } catch (PlayerAlreadyExistException e) {
            return "redirect:register?error";
        }
    }
}
