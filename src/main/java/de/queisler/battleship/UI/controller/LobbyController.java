package de.queisler.battleship.UI.controller;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LobbyController {
    //TODO: Async update task of table
    @Autowired
    PlayerService playerService;
    List<Player> players;

    public LobbyController() {
        players = players();
    }

    @GetMapping("/lobby")
    public String lobby(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player p = playerService.getPlayer(authentication.getName());
        players.add(p);
        model.addAttribute("players", players);
        return "lobby";
    }

    @Bean
    public List<Player> players() {
        return new ArrayList<>();
    }
}
