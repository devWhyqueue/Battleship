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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.data.services.PlayerService;

@Controller()
public class LobbyController
{
	@Autowired
	private PlayerService playerService;
	private Map<String, Player> players = new HashMap<>();
	private Map<String, Player> invitations = new HashMap<>(); // Map<Receiver, Sender>

	@GetMapping("/lobby")
	public String lobby(Model model)
	{
		Map<String, Player> tableData = new HashMap<>(players);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		tableData.remove(authentication.getName());
		model.addAttribute("players", new ArrayList<Player>(tableData.values()));
		return "lobby";
	}

	@GetMapping(value = "/lobby", params = "updateTable")
	public String updateTable(Model model)
	{
		Map<String, Player> tableData = new HashMap<>(players);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		tableData.remove(authentication.getName());
		model.addAttribute("players", new ArrayList<Player>(tableData.values()));
		return "lobby :: playerTable";
	}

	@GetMapping(value = "/lobby", params = "checkInvitations")
	public String updateInvitations(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Player sender = invitations.get(authentication.getName());
		model.addAttribute("invitingPlayer", sender);
		return "lobby :: invitedBy";
	}

	@GetMapping(value = "/lobby", params = "invite")
	public String sendInvitation(Model model, @RequestParam("invite") String invitedPlayer)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		invitations.put(invitedPlayer, playerService.getPlayer(authentication.getName()));
		return "redirect:lobby";
	}

	@EventListener
	public void handleLobbyEvents(ServletRequestHandledEvent e)
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
