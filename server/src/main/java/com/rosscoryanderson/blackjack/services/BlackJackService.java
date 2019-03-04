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
        Game gameCopy = new Game(game);
        match.dealCardToPlayer(gameCopy, index);
        gameCopy = evaluatePlayerHand(match, gameCopy, index);
        gameCopy = checkIfDealerHasActions(match, gameCopy);
        return gameCopy;
    }

    public Game evaluatePlayerHand(Match match, Game game, int index) {
        Game gameCopy = new Game(game);
        Hand hand = gameCopy.getPlayerHand(index);
        if (hand.getHandValue() > 21) {
            gameCopy.nextHand(index, HandStatus.BUSTED);
        } else if (hand.getHandValue() == 21) {
            if (hand.getHandSize() == 2) {
                gameCopy = payOutBlackJack(match, gameCopy, hand);
                gameCopy.nextHand(index, HandStatus.BLACKJACK);
            } else {
                gameCopy.nextHand(index, HandStatus.STAND);
            }
        }
        return gameCopy;
    }


    public Game standAction(Match match, Game game, int index) {
        Game gameCopy = new Game(game);
        gameCopy.nextHand(index, HandStatus.STAND);
        gameCopy = checkIfDealerHasActions(match, gameCopy);
        return gameCopy;
    }

    public Game doubleDownAction(Match match, Game game, int index) {
        Game gameCopy = new Game(game);
        Hand hand = gameCopy.getPlayerHand(index);

        if (gameCopy.canAffordToSplitOrDouble() && hand.getHandSize() == 2) {
            hand.setDoubleDown(true);
            gameCopy.setPlayerHand(hand, index);
            match.dealCardToPlayer(gameCopy, index);
            gameCopy = evaluatePlayerHand(match, gameCopy, index);
            if(gameCopy.getCurrentHandIndex() == index) {
                gameCopy.nextHand(index, HandStatus.STAND);
            }
            gameCopy = checkIfDealerHasActions(match, gameCopy);
        }
        return gameCopy;
    }

    public Game splitAction(Match match, Game game, int index) {
        Game gameCopy = new Game(game);
        Hand hand = gameCopy.getPlayerHand(index);
        if (gameCopy.playerCanSplit(hand) && gameCopy.canAffordToSplitOrDouble()) {
            int currentChipStack = match.getPlayerChipStack();
            match.setPlayerChipStack(currentChipStack - gameCopy.getBetAmount());
            gameCopy.splitCards(index);
        }
        return gameCopy;
    }

    public Game dealersTurnActions(Match match, Game game) {
        Game gameCopy = new Game(game);
        ArrayList<Hand> hands = gameCopy.getPlayerHands();
        Hand dealerHand = gameCopy.getDealerHand();
        dealerCardDraw(match, gameCopy, dealerHand);
        for (Hand hand : hands) {
            if (hand.getHandStatus() == HandStatus.STAND) {
                if (dealerHand.getHandValue() <= 21) {
                    gameCopy = checkIfWinner(match, gameCopy, hand, dealerHand);
                } else {
                    gameCopy = payOutWin(match, gameCopy, hand);
                }
            } else if (hand.getHandStatus() == HandStatus.BUSTED) {
                System.out.println("BUSTED CUSTARD");
                gameCopy = payOutLoss(match, gameCopy, hand);
            }
        }

        gameCopy.setGameInProgress(false);
        return gameCopy;
    }

    private void dealerCardDraw(Match match, Game game, Hand dealerHand) {
        if (dealerHand.getHandSize() == 1) {
            while (dealerHand.getLargestHandValue() <= 17) {
                match.dealCardToDealer(game);
            }
        }
    }

    public Game checkIfDealerHasActions(Match match, Game game) {
        Game gameCopy = new Game(game);
        if (!game.isPlayersTurn()) {
            gameCopy = dealersTurnActions(match, gameCopy);
        }
        return gameCopy;
    }

    private Game checkIfWinner(Match match, Game game, Hand player, Hand dealer) {
        Game gameCopy = new Game(game);
        int playerTotal = player.getLargestHandValue();
        int dealerTotal = dealer.getLargestHandValue();

        if (playerTotal > dealerTotal) {
            gameCopy = payOutWin(match, gameCopy, player);
        } else if (playerTotal == dealerTotal) {
            gameCopy = payOutPush(match, gameCopy, player);
        } else {
            gameCopy = payOutLoss(match, gameCopy, player);
        }

        return gameCopy;
    }

    private Game payOut(Match match, Game game, Hand hand, int payOutAmount) {
        System.out.println("Pay out amount: " + payOutAmount);
        Game gameCopy = new Game(game);
        int currentChipStack = match.getPlayerChipStack();
        int payOutTotal;
        int profitTotal;
        int betAmount = gameCopy.getBetAmount();
        if (hand.isDoubleDown()) {
            payOutTotal = payOutAmount * 2;
            System.out.println("hand is double down" + (payOutTotal < 0));
            if (payOutAmount < 0) {
                profitTotal = payOutTotal;
            } else {
                profitTotal = payOutTotal - (betAmount * 2);
            }
        } else {
            payOutTotal = payOutAmount;
            if (payOutAmount < 0) {
                profitTotal = payOutTotal;
            } else {
                profitTotal = payOutTotal - betAmount;
            }
        }
        System.out.println("profit amount " + profitTotal);
        gameCopy.addToProfit(profitTotal);
        if (payOutAmount < 0) {
            if (hand.isDoubleDown()) {
                match.setPlayerChipStack(currentChipStack + payOutAmount);
                gameCopy.setChipStack(currentChipStack + payOutAmount);
            } else {
                match.setPlayerChipStack(currentChipStack);
                gameCopy.setChipStack(currentChipStack);
            }

        } else {
            match.setPlayerChipStack(currentChipStack + payOutTotal);
            gameCopy.setChipStack(currentChipStack + payOutTotal);
        }
        return gameCopy;
    }

    private Game payOutBlackJack(Match match, Game game, Hand hand) {
        Game gameCopy = new Game(game);
        int betAmount = gameCopy.getBetAmount();
        // add 0.5 to round up on $5 bets.
        // TODO: Look into a different solution
        //  for this as it creates exploitable strategies around using
        //  multiples of 5, distorting the payout rate.
        Double payOutAmount = ((3 / 2) * betAmount) + betAmount + 0.5;
        gameCopy = payOut(match, gameCopy, hand, payOutAmount.intValue());
        return gameCopy;
    }

    private Game payOutWin(Match match, Game game, Hand hand) {
        Game gameCopy = new Game(game);
        int betAmount = gameCopy.getBetAmount();
        int payOutAmount = (betAmount * 2);
        gameCopy = payOut(match, gameCopy, hand, payOutAmount);
        return gameCopy;
    }

    private Game payOutPush(Match match, Game game, Hand hand) {
        Game gameCopy = new Game(game);
        int betAmount = gameCopy.getBetAmount();
        int payOutAmount = betAmount;
        gameCopy = payOut(match, gameCopy, hand, payOutAmount);
        return gameCopy;
    }

    private Game payOutLoss(Match match, Game game, Hand hand) {
        Game gameCopy = new Game(game);
        int betAmount = gameCopy.getBetAmount();
        int payOutAmount = betAmount * -1;
        gameCopy = payOut(match, gameCopy, hand, payOutAmount);
        return gameCopy;
    }

}
