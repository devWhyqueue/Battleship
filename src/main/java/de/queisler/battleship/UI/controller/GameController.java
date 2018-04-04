package de.queisler.battleship.UI.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.queisler.battleship.businessLogic.enums.PointStatus;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.model.Game;
import de.queisler.battleship.businessLogic.model.GameManagement;
import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.businessLogic.model.Point;

@Controller
public class GameController
{
	@Autowired
	private GameManagement gameManagement;

	@GetMapping("/game")
	public String game(Model model) throws GameException
	{
		Player p = getPlayerFromAuthentication();
		Game g = gameManagement.getGame(p);
		try
		{
			g.startGame();
		}
		catch (GameException e)
		{
			// Game already started by opponent
		}

		PointStatus[][] shipMap = p.getFleet().getShipMap().getPointStatusArray();
		List<PointStatus> list = new ArrayList<PointStatus>();
		for (int i = 0; i < shipMap.length; i++)
			list.addAll(Arrays.asList(shipMap[i]));
		model.addAttribute("myPoints", list);

		PointStatus[][] hitMap = p.getHitMap().getPointStatusArray();
		List<PointStatus> list2 = new ArrayList<PointStatus>();
		for (int i = 0; i < hitMap.length; i++)
			list2.addAll(Arrays.asList(hitMap[i]));

		System.err.println(Arrays.toString(list2.toArray()));
		model.addAttribute("opPoints", list2);

		return "game";
	}

	@GetMapping(value = "/game", params = "enemyFieldMap")
	public String updateEnemyFieldMap(Model model)
	{
		Player p = getPlayerFromAuthentication();

		PointStatus[][] hitMap = p.getHitMap().getPointStatusArray();
		List<PointStatus> list2 = new ArrayList<PointStatus>();
		for (int i = 0; i < hitMap.length; i++)
			list2.addAll(Arrays.asList(hitMap[i]));
		model.addAttribute("opPoints", list2);

		return "game :: enemyFieldMap";
	}

	@PostMapping(value = "/game")
	public ResponseEntity<?> attack(Model model, @RequestParam("attack") int pt)
		throws GameException, InvalidPointException
	{
		Player p = getPlayerFromAuthentication();
		Game g = gameManagement.getGame(p);
		int row = pt / 10 + 1;
		int column = pt % 10 + 1;
		g.attack(p, new Point(row, column));

		return ResponseEntity.ok().build();
	}

	@ExceptionHandler({ GameException.class })
	public void handleException(Exception e)
	{

	}

	private Player getPlayerFromAuthentication()
	{
		return (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
