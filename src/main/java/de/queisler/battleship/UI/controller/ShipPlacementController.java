package de.queisler.battleship.UI.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.queisler.battleship.businessLogic.enums.Alignment;
import de.queisler.battleship.businessLogic.enums.ShipType;
import de.queisler.battleship.businessLogic.exceptions.FleetException;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPositionException;
import de.queisler.battleship.businessLogic.model.Fleet;
import de.queisler.battleship.businessLogic.model.GameManagement;
import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.businessLogic.model.Point;
import de.queisler.battleship.businessLogic.model.Ship;

@Controller()
public class ShipPlacementController {
    @Autowired
    private GameManagement gameManagement;

    @GetMapping("/placeShips")
    public String placeShips(Model model) {
        return "placeShips";
    }

    @GetMapping(value = "/placeShips", params = "isGameReady")
    public String updateInvitations(Model model) throws GameException {
        boolean gameReady = gameManagement.getGame(getPlayerFromAuthentication()).isReady();
        model.addAttribute("gameReady", gameReady);

        return "placeShips :: gameReady";
    }

    @PostMapping(value = "/placeShips")
    public ResponseEntity<?> getPlacement(Model model, @RequestParam("shipPlacement") String ships) throws
			ParseException, FleetException, InvalidPositionException, InvalidPointException {
        String decShips = StringUtils.newStringUtf8(Base64.decodeBase64(ships));

        Player p = getPlayerFromAuthentication();
        p.setFleet(new Fleet());

        JSONArray shipsArr = (JSONArray) new JSONParser().parse(decShips);
        boolean cruiserAdded = false;
        for (int i = 0; i < shipsArr.size(); i++) {
            JSONObject ship = (JSONObject) shipsArr.get(i);
            long x = (Long) ship.get("x") + 1;
            long y = (Long) ship.get("y") + 1;
            long width = (Long) ship.get("width");
            long height = (Long) ship.get("height");
            Alignment a = width < height ? Alignment.VERTICAL : Alignment.HORIZONTAL;
            ShipType t = null;
            long size = a == Alignment.VERTICAL ? height : width;
            switch (Math.toIntExact(size)) {
                case 5:
                    t = ShipType.CARRIER;
                    break;
                case 4:
                    t = ShipType.BATTLESHIP;
                    break;
                case 3:
                    if (!cruiserAdded) {
                        t = ShipType.CRUISER;
                        cruiserAdded = true;
                    } else t = ShipType.SUBMARINE;
                    break;
                case 2:
                    t = ShipType.DESTROYER;
                    break;
            }
            p.getFleet().addShip(new Ship(t, new Point(Math.toIntExact(y), Math.toIntExact(x)), a));
        }
        return ResponseEntity.ok("Placement completed");
    }

    @ExceptionHandler({FleetException.class, InvalidPositionException.class, InvalidPointException.class, GameException.class})
    public ResponseEntity<?> handleException(Exception e) {
        if (e instanceof GameException) return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).build();

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private Player getPlayerFromAuthentication() {
        return (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
