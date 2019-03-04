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

    @PostMapping("/hit/{index}")
    public ResponseEntity<Game> hitAction(@PathVariable int index) {
        game = blackJackService.hitAction(match, game, index);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @PostMapping("/stand/{index}")
    public ResponseEntity<Game> standAction(@PathVariable int index) {
        game = blackJackService.standAction(match, game, index);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @PostMapping("/doubleDown/{index}")
    public ResponseEntity<?> doubleDownAction(@PathVariable int index) {
        game = blackJackService.doubleDownAction(match, game, index);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @PostMapping("/split/{index}")
    public ResponseEntity<?> splitAction(@PathVariable int index) {
        game = blackJackService.splitAction(match, game, index);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

}
