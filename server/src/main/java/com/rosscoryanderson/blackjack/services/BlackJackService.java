package com.rosscoryanderson.blackjack.services;

import com.rosscoryanderson.blackjack.blackjack.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class BlackJackService {

    public Match createMatch() {
        Match match = new Match();
        return match;
    }

    public Game createGame(Match match, int betAmount) {
        return match.newGame(betAmount);
    }

    // When split is true, the action will be passed to the split hand.
    public Game hitAction(Match match, Game game, Boolean split) {

        if (split && game.isSplit()) {
            match.dealCardToPlayerSplit(game);
        } else {
            match.dealCardToPlayer(game);
        }
        Game returnGame = checkGameStatus(match, game);
        return game;
    }

    public Game standAction(Game game, Boolean split) {
        Game standGame = game;
        if (split) {
            if(standGame.isSplit()) {
                standGame.setDoubleDownSplit(true);
            }
        } else {
            standGame.setDoubleDown(true);
        }
        Game returnGame =
        return null;
    }

    public Game doubleDownAction(Match match, Game game, Boolean split) {
        Game doubleGame = game;
        if(doubleGame.canAffordToSplitOrDouble()) {
            if (split) {
                if(doubleGame.isSplit()) {
                    doubleGame.setDoubleDownSplit(true);
                }
            } else {
                doubleGame.setDoubleDown(true);
            }
        }
        Game returnGame = checkGameStatus(match, game);
        return returnGame;
    }

    public Game splitAction(Game game) {
        Game returnGame = game;
        returnGame.splitCards();
        return returnGame;
    }

    public Game resetGame() {
        return null;
    }

    private Game checkGameStatus(Match match, Game game) {
        Game returnGame = game;
        //
        if(returnGame.getGameStatus() != GameStatus.IN_PROGRESS) {
            return returnGame;
        }
        if(returnGame.isPlayersTurn()) {
            if (game.isSplit()) {
                checkSplitStatus(match, returnGame);
            }
            if (checkHandStatus(returnGame.getPlayerHand()) == HandStatus.BUSTED) {
                if (checkHandStatus(returnGame.getPlayerHandSplit()) == HandStatus.VALID) {
                    returnGame.removeSplit(returnGame.getPlayerHand(), returnGame.getPlayerHandSplit());
                } else {
                    returnGame.setGameStatus(GameStatus.DEALER_WIN);
                }
            } else if (checkHandStatus(returnGame.getPlayerHand()) == HandStatus.BLACKJACK) {
                payOutBlackJack(match, returnGame);
            }
        } else {
            Hand dealersHand = returnGame.getDealerHand();
            while(dealersHand.getHandValue() < 17) {
                match.dealCardToDealer(returnGame);
            }
            if(dealersHand.getHandValue() >=17 && dealersHand.getHandValue() < 21) {

            }
        }
        return returnGame;
    }

    private void checkSplitStatus(Match match, Game game) {
        if (checkHandStatus(game.getPlayerHandSplit()) == HandStatus.BUSTED) {
            game.setSplit(false);
        } else if (checkHandStatus(game.getPlayerHandSplit()) == HandStatus.BLACKJACK) {
            payOutBlackJack(match, game);
            game.setSplit(false);
        }
    }

    private HandStatus checkHandStatus(Hand hand) {
        if (hand.getHandValue() > 21) {
            return HandStatus.BUSTED;
        } else {
            if (hand.getHandValue() == 21) {
                return HandStatus.BLACKJACK;
            }
        }
        return HandStatus.VALID;
    }

    private void payOutBlackJack(Match match, Game game) {
        int betAmount = game.getBetAmount();
        // add 0.5 to round up on $5 bets. Look into a different solution
        // for this as it creates exploitable strategies around using
        // multiples of 5, distorting the payout rate.
        Double payOut = ((3 / 2) * betAmount) + betAmount + 0.5;
        int currentStack = match.getPlayerChipStack();
        match.setPlayerChipStack(currentStack + payOut.intValue());
    }

    private void payOutWin(Match match, Game game) {
        int betAmount = game.getBetAmount();
        int currentStack = match.getPlayerChipStack();
        match.setPlayerChipStack(currentStack + (betAmount * 2));
    }

    private void payOutPush(Match match, Game game) {
        int betAmount = game.getBetAmount();
        int currentStack = match.getPlayerChipStack();
        match.setPlayerChipStack(currentStack + betAmount);
    }

    private void compareHands(Hand playerHand, Hand dealerHand, Match match, Game game) {
        if(checkHandStatus(playerHand) == HandStatus.VALID && checkHandStatus(dealerHand) == HandStatus.VALID ) {
            if (playerHand.getHandValue() > dealerHand.getHandValue()) {
                payOutWin(match, game);
                game.setGameStatus(GameStatus.GAME_ENDED_WITH_SPLIT);
            } else if (playerHand.getHandValue() == dealerHand.getHandValue()) {
                payOutPush(match, game);
            }
        }
        if(game.isSplit()) {
            game.setGameStatus(GameStatus.GAME_ENDED_WITH_SPLIT);
        }
    }


}
