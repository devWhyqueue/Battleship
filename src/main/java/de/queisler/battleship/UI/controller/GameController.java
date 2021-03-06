package de.queisler.battleship.UI.controller;

import de.queisler.battleship.businessLogic.enums.AttackResult;
import de.queisler.battleship.businessLogic.enums.PointStatus;
import de.queisler.battleship.businessLogic.exceptions.GameException;
import de.queisler.battleship.businessLogic.exceptions.InvalidPointException;
import de.queisler.battleship.businessLogic.model.Game;
import de.queisler.battleship.businessLogic.model.GameManagement;
import de.queisler.battleship.businessLogic.model.Player;
import de.queisler.battleship.businessLogic.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {
    @Autowired
    private GameManagement gameManagement;

    @GetMapping("/game")
    public String game(Model model){
        try {
            Player p = getPlayerFromAuthentication();
            Game g = gameManagement.getGame(p);
            g.startGame();

            PointStatus[][] shipMap = p.getFleet().getShipMap().getPointStatusArray();
            List<PointStatus> list = new ArrayList<PointStatus>();
            for (int i = 0; i < shipMap.length; i++)
                list.addAll(Arrays.asList(shipMap[i]));
            model.addAttribute("myPoints", list);

            PointStatus[][] hitMap = p.getHitMap().getPointStatusArray();
            List<PointStatus> list2 = new ArrayList<PointStatus>();
            for (int i = 0; i < hitMap.length; i++)
                list2.addAll(Arrays.asList(hitMap[i]));
            model.addAttribute("opPoints", list2);

            return "game";
        }
        catch(GameException e){
            return "redirect:lobby";
        }
    }

    @GetMapping(value = "/game", params = "ownFieldMap")
    public String updateOwnFieldMap(Model model) throws GameException {
        Player p = getPlayerFromAuthentication();
        Game g = gameManagement.getGame(p);
        Player o = g.getOpponent(p);

        PointStatus[][] advancedShipMap = p.getFleet().getShipMap().getPointStatusArray(); // own ships
        PointStatus[][] opHitMap = o.getHitMap().getPointStatusArray(); // enemy's hit map
        for (int row = 0; row < opHitMap.length; row++) {
            for (int column = 0; column < opHitMap[0].length; column++) {
                if (opHitMap[row][column] == PointStatus.SHIP && advancedShipMap[row][column].getShipType() != null) {
                    advancedShipMap[row][column] = PointStatus.SHIP;
                }
                else if(opHitMap[row][column] == PointStatus.WATER){
                    advancedShipMap[row][column] = PointStatus.WATER;
                }
            }
        }
        List<PointStatus> list = new ArrayList<>();
        for (int i = 0; i < advancedShipMap.length; i++)
            list.addAll(Arrays.asList(advancedShipMap[i]));
        model.addAttribute("myPoints", list);

        return "game :: ownFieldMap";
    }

    @GetMapping(value = "/game", params = "enemyFieldMap")
    public String updateEnemyFieldMap(Model model) {
        Player p = getPlayerFromAuthentication();

        PointStatus[][] hitMap = p.getHitMap().getPointStatusArray();
        List<PointStatus> list2 = new ArrayList<PointStatus>();
        for (int i = 0; i < hitMap.length; i++)
            list2.addAll(Arrays.asList(hitMap[i]));
        model.addAttribute("opPoints", list2);

        return "game :: enemyFieldMap";
    }

    @GetMapping(value = "/game", params = "updateTurnStatus")
    public ResponseEntity<?> updateTurnStatus(Model model) throws GameException {
        Player p = getPlayerFromAuthentication();
        Game g = gameManagement.getGame(p);

        if (g.determineWinner() != null){
            gameManagement.removeGame(g);

            return ResponseEntity.status(210).build(); // Game lost
        }

        if (g.getCurrentPlayer().equals(p)) return ResponseEntity.status(211).build(); // Player's turn
        else return ResponseEntity.status(212).build(); // Opponent's turn
    }

    @PostMapping(value = "/game")
    public ResponseEntity<?> attack(Model model, @RequestParam("attack") int pt) throws GameException,
            InvalidPointException {
        Player p = getPlayerFromAuthentication();
        Game g = gameManagement.getGame(p);
        int row = pt / 10 + 1;
        int column = pt % 10 + 1;
        AttackResult r = g.attack(p, new Point(row, column));

        if (r.name().startsWith("SUNK"))
            return ResponseEntity.status(213).body("You destroyed your opponent's " + g.getOpponent(p).getFleet()
                    .getShip(r.getShipType()).toString()); // DESTROYED
        else if (r == AttackResult.LOST){
            return ResponseEntity.status(214).body("Congratulations! You won the game!");
        }
        else if (r == AttackResult.HIT) return ResponseEntity.status(215).build();
        else return ResponseEntity.status(216).build(); // MISS

    }

    @ExceptionHandler({GameException.class})
    public void handleException(Exception e) {
        if (e.getMessage().equals("Der Gegner ist gerade am Zug!")) {
            // unauthorized turn attempt
        } else e.printStackTrace();
    }

    private Player getPlayerFromAuthentication() {
        return (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
