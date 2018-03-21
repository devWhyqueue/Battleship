package de.queisler.battleship.UI.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.services.PlayerService;

@Controller("lobbyController")
public class LobbyController
{
	//TODO: Async update task of table
	@Autowired
	private PlayerService playerService;
	private Map<String, Player> players = new HashMap<>();

	@GetMapping("/lobby")
	public String lobby(Model model)
	{
		Map<String, Player> tableData = new HashMap<>(players);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		tableData.remove(authentication.getName());
		model.addAttribute("players", new ArrayList<Player>(tableData.values()));
		return "lobby";
	}

	@GetMapping(value = "/lobby", params = "update")
	public String tableUpdate(Model model)
	{
		Map<String, Player> tableData = new HashMap<>(players);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		tableData.remove(authentication.getName());
		model.addAttribute("players", new ArrayList<Player>(tableData.values()));
		return "lobby :: playerTable";
	}

	@EventListener
	public void handleEvent(ServletRequestHandledEvent e)
	{
		String username = e.getUserName();
		String url = e.getRequestUrl();

		if (username != null)
		{
			if (url.equals("/lobby"))
				players.put(username, playerService.getPlayer(username));
			else
				players.remove(username);
		}
	}
}
