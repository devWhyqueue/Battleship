package de.queisler.battleship.UI.controller;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LobbyController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/lobby")
    public String lobby(Model model) {
        List<Player> p = playerService.getAllPlayers();
        p.addAll(p);
        p.addAll(p);
        model.addAttribute("players", p);
        return "lobby";
    }
}
