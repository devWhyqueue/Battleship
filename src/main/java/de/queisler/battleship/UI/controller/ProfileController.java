package de.queisler.battleship.UI.controller;

import de.queisler.battleship.businessLogic.model.Player;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController
{
    @GetMapping("/profile")
    public String profile(Model model)
    {
        model.addAttribute("p", getPlayerFromAuthentication());
        return "profile";
    }

    private Player getPlayerFromAuthentication()
    {
        return (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
