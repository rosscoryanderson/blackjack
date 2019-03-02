package com.rosscoryanderson.blackjack.controllers;

import com.rosscoryanderson.blackjack.blackjack.*;
import com.rosscoryanderson.blackjack.services.BlackJackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blackjack")
public class BlackJackController {
    private Match match;
    private Game game;

    @Autowired
    private BlackJackService blackJackService;

    @PostMapping("/")
    public ResponseEntity<?> createNewMatch() {
        match = blackJackService.createMatch();
        String message = "Match Created";
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @PostMapping("/newGame/{betAmount}")
    public ResponseEntity<?> createNewGame(@PathVariable int betAmount) {
        Integer bet = Integer.valueOf(betAmount);
        if(match == null){
            match = blackJackService.createMatch();
        }

        if(bet > match.getPlayerChipStack()) {
            String errorMessage = "Bet amount too large";
            return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
        } else if (bet < 0) {
            String errorMessage = "Bet must be a positive value";
            return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        game = blackJackService.createGame(match, bet);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @PostMapping("/hit")
    public ResponseEntity<Game> hitAction() {

        return null;
    }

    @PostMapping("/stand")
    public ResponseEntity<Game> standAction() {
        return null;
    }

    @PostMapping("/doubleDown")
    public ResponseEntity<?> doubleDownAction() {
        return null;
    }

    @PostMapping("/split")
    public ResponseEntity<?> splitAction() {
        game = blackJackService.splitAction(game);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @PostMapping("/hit/split")
    public ResponseEntity<Game> hitOnSplitAction() {
        return null;
    }

    @PostMapping("/stand/split")
    public ResponseEntity<Game> standOnSplitAction() {
        return null;
    }

    @PostMapping("/doubleDown/split")
    public ResponseEntity<?> doubleDownOnSplitAction() {
        return null;
    }
}
