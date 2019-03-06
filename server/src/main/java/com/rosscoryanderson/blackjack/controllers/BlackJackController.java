package com.rosscoryanderson.blackjack.controllers;

import com.rosscoryanderson.blackjack.blackjack.*;
import com.rosscoryanderson.blackjack.services.BlackJackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blackjack")
@CrossOrigin
public class BlackJackController {
    private GameEngine gameEngine;
    private Game game;

    @Autowired
    private BlackJackService blackJackService;

    @PostMapping("/")
    public ResponseEntity<?> createNewMatch() {
        gameEngine = blackJackService.createMatch();
        String message = "GameEngine Created";
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @PostMapping("/newGame/{betAmount}")
    public ResponseEntity<?> createNewGame(@PathVariable int betAmount) {
        Integer bet = Integer.valueOf(betAmount);
        if(gameEngine == null){
            gameEngine = blackJackService.createMatch();
        }

        if(bet > gameEngine.getPlayerChipStack()) {
            String errorMessage = "Bet amount too large";
            return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
        } else if (bet < 0) {
            String errorMessage = "Bet must be a positive value";
            return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        gameEngine = blackJackService.createGame(gameEngine, bet);
        return new ResponseEntity<GameEngine>(gameEngine, HttpStatus.OK);
    }

    @PostMapping("/hit")
    public ResponseEntity<GameEngine> hitAction() {
        gameEngine = blackJackService.hitAction(gameEngine);
        return new ResponseEntity<GameEngine>(gameEngine, HttpStatus.OK);
    }

    @PostMapping("/stand")
    public ResponseEntity<GameEngine> standAction() {
        gameEngine = blackJackService.standAction(gameEngine);
        return new ResponseEntity<GameEngine>(gameEngine, HttpStatus.OK);
    }

    @PostMapping("/doubleDown")
    public ResponseEntity<GameEngine> doubleDownAction() {
        gameEngine = blackJackService.doubleDownAction(gameEngine);
        return new ResponseEntity<GameEngine>(gameEngine, HttpStatus.OK);
    }

    @PostMapping("/split")
    public ResponseEntity<GameEngine> splitAction() {
        gameEngine = blackJackService.splitAction(gameEngine);
        return new ResponseEntity<GameEngine>(gameEngine, HttpStatus.OK);
    }

}
