package com.rosscoryanderson.blackjack.services;

import com.rosscoryanderson.blackjack.blackjack.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Service
public class BlackJackService {

    public Match createMatch() {
        Match match = new Match();
        return match;
    }

    public Game createGame(Match match, int betAmount) {
        return match.newGame(betAmount);
    }

    public Game hitAction(Match match, Game game, int index) {
        Game gameCopy = game;
        match.dealCardToPlayer(gameCopy, index);
        Hand hand = gameCopy.getPlayerHand(index);
        if(hand.getHandValue() > 21) {
            gameCopy.nextHand(index, HandStatus.BUSTED);
        } else if (hand.getHandValue() == 21) {
            if (hand.getHandSize() == 2) {
                payOutBlackJack(match, gameCopy, hand);
                gameCopy.nextHand(index, HandStatus.BLACKJACK);
            } else {
                gameCopy.nextHand(index, HandStatus.STAND);
            }
        }
        Game returnGame = checkIfDealerHasActions(match, gameCopy);
        return returnGame;
    }

    public Game standAction(Match match, Game game, int index) {
        Game gameCopy = game;

        gameCopy.nextHand(index, HandStatus.STAND);
        Game returnGame = checkIfDealerHasActions(match, gameCopy);
        return returnGame;
    }

    public Game doubleDownAction(Match match, Game game, int index) {
        Game returnGame = game;
        Hand hand = returnGame.getPlayerHand(index);

        if(returnGame.canAffordToSplitOrDouble() && hand.getHandSize() == 2) {
            returnGame = hitAction(match, returnGame, index);
            hand.setDoubleDown(true);
            returnGame.setPlayerHand(hand, index);
            returnGame.nextHand(index, HandStatus.STAND);
            returnGame = checkIfDealerHasActions(match, returnGame);
        }
        return returnGame;
    }

    public Game splitAction(Game game, int index) {
        Game returnGame = game;
        returnGame.splitCards(index);
        return returnGame;
    }

    public Game dealersTurnActions(Match match, Game game) {
        Game gameCopy = game;
        ArrayList<Hand> hands = gameCopy.getPlayerHands();
        Hand dealerHand = gameCopy.getDealerHand();
        dealerCardDraw(match, gameCopy, dealerHand);
        if(dealerHand.getHandValue() <= 21) {
            for(Hand hand: hands) {
                if (hand.getHandStatus() == HandStatus.STAND) {
                    checkIfWinner(match, game, hand, dealerHand);
                }
            }
        } else {
            // If dealer busted, do not send dealt cards to client. Return the original game state.
            game.setGameInProgress(false);
            return game;
        }
        gameCopy.setGameInProgress(false);
        return gameCopy;
    }

    private void dealerCardDraw(Match match, Game game, Hand dealerHand) {
        if(dealerHand.getHandSize() == 1) {
            while(dealerHand.getLargestHandValue() <= 17) {
                match.dealCardToDealer(game);
            }
        }
    }

    public Game checkIfDealerHasActions(Match match, Game game) {
        Game gameCopy = game;
        if(!game.isPlayersTurn()) {
            gameCopy = dealersTurnActions(match, gameCopy);
        }
        return gameCopy;
    }

    private void checkIfWinner(Match match, Game game, Hand player, Hand dealer) {
        int playerTotal = player.getLargestHandValue();
        int dealerTotal = dealer.getLargestHandValue();

        if(playerTotal > dealerTotal) {
            payOutWin(match, game);
        } else if (playerTotal == dealerTotal) {
            payOutPush(match, game);
        } else {
            game.addToProfit(-5);
        }
    }

    private void payOut(Match match, Game game, Hand hand, int currentStack, int payOutAmount) {
        int payOutTotal;
        int betAmount = game.getBetAmount();
        if(hand.isDoubleDown()) {
            payOutTotal = payOutAmount * 2;
            betAmount = betAmount * 2;
        } else {
            payOutTotal = payOutAmount;
        }
        game.addToProfit(payOutTotal - betAmount);
        match.setPlayerChipStack(currentStack + payOutTotal);
    }

    private void payOutBlackJack(Match match, Game game, Hand hand) {
        int betAmount = game.getBetAmount();
        // add 0.5 to round up on $5 bets.
        // TODO: Look into a different solution
        //  for this as it creates exploitable strategies around using
        //  multiples of 5, distorting the payout rate.
        Double payOutAmount = ((3 / 2) * betAmount) + betAmount + 0.5;
        int currentStack = match.getPlayerChipStack();
        payOut(match, game, hand, currentStack, payOutAmount.intValue());
    }

    private void payOutWin(Match match, Game game) {
        int betAmount = game.getBetAmount();
        int currentStack = match.getPlayerChipStack();
        int payOutTotal = (betAmount * 2);
        match.setPlayerChipStack(currentStack + payOutTotal);
        game.addToProfit(payOutTotal - betAmount);
    }

    private void payOutPush(Match match, Game game) {
        int betAmount = game.getBetAmount();
        int currentStack = match.getPlayerChipStack();
        match.setPlayerChipStack(currentStack + betAmount);
    }

}
