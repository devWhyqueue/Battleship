package de.queisler.battleship.UI.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.queisler.battleship.data.services.PlayerService;

@Controller()
public class ShipPlacementController
{
	@Autowired
	PlayerService playerService;

	@GetMapping("/placeShips")
	public String placeShips(Model model)
	{
		return "placeShips";
	}

	@GetMapping(value = "/placeShips", params = "ships")
	public String getPlacement(Model model, @RequestParam("ships") String ships)
	{
		String decShips = StringUtils.newStringUtf8(Base64.decodeBase64(ships));
		try
		{
			JSONArray shipsArr = (JSONArray) new JSONParser().parse(decShips);
			for (int i = 0; i < shipsArr.size(); i++)
			{
				JSONObject ship = (JSONObject) shipsArr.get(i);
				System.out.println("SHIP: " + ship);
				//				p.setFleet(new Fleet());
				//				p.getFleet().addShip(new Ship(shipType,
				//					new Point(Integer.parseInt(ship.get("x").toString()), Integer.parseInt(ship.get("y").toString())),
				//					alignment));

			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "placeShips";
	}
}
