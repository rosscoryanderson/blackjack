package com.rosscoryanderson.blackjack.services;

import com.rosscoryanderson.blackjack.blackjack.*;
import org.springframework.stereotype.Service;

@Service
public class BlackJackService {

    public GameEngine createMatch() {
        GameEngine gameEngine = new GameEngine();
        return gameEngine;
    }

    public GameEngine createGame(GameEngine gameState, int betAmount) {
        GameEngine gameEngine = new GameEngine(gameState);
        gameEngine.newGame(betAmount);
        return gameEngine;
    }

    public GameEngine hitAction(GameEngine gameState) {
        GameEngine gameEngine = new GameEngine(gameState);
        gameEngine.dealCardToPlayer();
        gameEngine.checkIfPlayerBusted();
        gameEngine.checkIfPlayerStandsOnTwentyOne();
        gameEngine.checkIfDealerHasActions();
        return gameEngine;
    }

    public GameEngine standAction(GameEngine gameState) {
        GameEngine gameEngine = new GameEngine(gameState);
        gameEngine.setHandToStand();
        gameEngine.checkIfDealerHasActions();
        return gameEngine;
    }

    public GameEngine doubleDownAction(GameEngine gameState) {
        GameEngine gameEngine = new GameEngine(gameState);
        if (gameEngine.canAffordToSplitOrDoubleDown()) {
            gameEngine.setHandToDoubleDown();
            gameEngine.dealCardToPlayer();
            gameEngine.checkIfPlayerBusted();
            gameEngine.setHandToStand();
            gameEngine.checkIfDealerHasActions();
        }
        return gameEngine;
    }

    public GameEngine splitAction(GameEngine gameState) {
        GameEngine gameEngine = new GameEngine(gameState);
        if (gameEngine.canAffordToSplitOrDoubleDown() && gameEngine.canSplitHand()) {
            gameEngine.splitCards();
        }
        return gameEngine;
    }

}
