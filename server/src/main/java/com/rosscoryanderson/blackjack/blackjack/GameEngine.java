package com.rosscoryanderson.blackjack.blackjack;

import java.util.ArrayList;

public class GameEngine {

    private int playerChipStack;
    private int betAmount;
    private int profit;
    private Shoe shoe;
    private Game game;

    public GameEngine() {
        shoe = new Shoe();
        // Default player chips to $100.
        playerChipStack = 100;
        betAmount = 5;
        profit = 0;
    }

    // Copy constructor
    public GameEngine(GameEngine gameEngineCopy) {
        this.playerChipStack = gameEngineCopy.playerChipStack;
        this.betAmount = gameEngineCopy.betAmount;
        this.profit = gameEngineCopy.profit;
        this.shoe = gameEngineCopy.shoe;
        this.game = gameEngineCopy.game;
    }

    public void newGame(int betAmount) {
        game = new Game();
        this.betAmount = betAmount;
        playerChipStack = playerChipStack - betAmount;
        profit = 0;
        dealCardToPlayer();
        dealCardToDealer();
        dealCardToPlayer();
        checkIfPlayerHasBlackJack();
        checkIfDealerHasActions();
    }

    public int getBetAmount() {
        return betAmount;
    }

    public int getProfit() {
        return profit;
    }

    public Game getGame() {
        return game;
    }

    public int getPlayerChipStack() {
        return playerChipStack;
    }

    public void dealCardToPlayer() {
        game.dealCardToPlayer(shoe.drawCard());
    }

    public void dealCardToDealer() {
        game.dealCardToDealer(shoe.drawCard());
    }

    public void checkIfPlayerHasBlackJack() {
        Hand playerHand = game.getPlayerHand();
        if (playerHand.getLargestHandValue() == 21 && playerHand.getHandSize() == 2) {
            game.nextHand(HandStatus.BLACKJACK);
        }
    }

    public void checkIfPlayerBusted() {
        Hand playerHand = game.getPlayerHand();
        if (playerHand.getHandValue() > 21) {
            game.nextHand(HandStatus.BUSTED);
        }
    }

    public void checkIfPlayerStandsOnTwentyOne() {
        Hand playerHand = game.getPlayerHand();
        if (playerHand.getLargestHandValue() == 21) {
            game.nextHand(HandStatus.STAND);
        }
    }

    public void checkIfDealerHasActions() {
        if (!game.isPlayersTurn()) {
            dealerCardDraw();
            calculateWinner();
            game.setGameInProgress(false);
        }
    }

    public void setHandToStand() {
        game.nextHand(HandStatus.STAND);
    }

    public void setHandToDoubleDown() {
        playerChipStack = playerChipStack - betAmount;
        Hand hand = game.getPlayerHand();
        hand.setDoubleDown(true);
        game.setPlayerHand(hand);
    }

    public boolean canAffordToSplitOrDoubleDown() {
        return (playerChipStack - betAmount) >= 0;
    }

    public boolean canSplitHand() {
        Hand playerHand = game.getPlayerHand();
        Card firstCardDealt = playerHand.getCardByPosition(0);
        Card secondCardDealt = playerHand.getCardByPosition(1);
        if (playerHand.getHandSize() == 2) {
            if (firstCardDealt.getName().equals(secondCardDealt.getName())) {
                return true;
            }
        }
        return false;
    }

    public void splitCards() {
        playerChipStack = playerChipStack - betAmount;
        game.splitCards();
    }

    private void calculateWinner() {
        ArrayList<Hand> playerHands = game.getPlayerHands();
        Hand dealerHand = game.getDealerHand();
        for (Hand hand : playerHands) {
            switch (hand.getHandStatus()) {
                case STAND:
                    if (dealerHand.getHandValue() <= 21) {
                        compareHands(hand);
                    } else {
                        payOutWin(hand);
                    }
                    break;
                case BLACKJACK:
                    payOutBlackJack(hand);
                    break;
                case BUSTED:
                    payOutLoss(hand);
                    break;
                default:
                    break;
            }
        }
    }

    private void compareHands(Hand playerHand) {
        Hand dealerHand = game.getDealerHand();
        int playerTotal = playerHand.getLargestHandValue();
        int dealerTotal = dealerHand.getLargestHandValue();

        if (playerTotal > dealerTotal) {
            payOutWin(playerHand);
        } else if (playerTotal == dealerTotal) {
            payOutPush(playerHand);
        } else {
            payOutLoss(playerHand);
        }
    }

    public void dealerCardDraw() {
        Hand dealerHand = game.getDealerHand();
        if (dealerHand.getHandSize() == 1) {
            while (dealerHand.getLargestHandValue() <= 17) {
                dealCardToDealer();
            }
        }
    }

    private void payOutBlackJack(Hand playerHand) {
        Double payOutAmount = ((3 * betAmount) / 2) + betAmount +  0.5;
        payOut(playerHand, payOutAmount.intValue());
    }

    private void payOutWin(Hand playerHand) {
        int payOutAmount = (betAmount * 2);
        payOut(playerHand, payOutAmount);
    }

    private void payOutPush(Hand playerHand) {
        int payOutAmount = betAmount;
        payOut(playerHand, payOutAmount);
    }

    private void payOutLoss(Hand playerHand) {
        int payOutAmount = (betAmount * -1);
        payOut(playerHand, payOutAmount);
    }

    private void payOut(Hand playerHand, int amount) {
        int profitAmount;
        int payOutAmount = amount;
        int valueOfBets = betAmount;
        if (playerHand.isDoubleDown()) {
            payOutAmount *= 2;
            valueOfBets *= 2;
        }
        if (payOutAmount < 0) {
            profitAmount = payOutAmount;
        } else {
            profitAmount = payOutAmount - valueOfBets;
            playerChipStack += payOutAmount;
        }
        profit += profitAmount;
    }
}