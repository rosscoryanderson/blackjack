package com.rosscoryanderson.blackjack.blackjack;

import java.util.ArrayList;

public class Game {
    // A game manages a single hand of BlackJack, while a match continues until the player is broke.

    private ArrayList<Hand> playerHands;
    private Hand dealerHand;
    private boolean gameInProgress;
    private boolean playersTurn;
    private int betAmount;
    private int chipStack;
    private int currentHandIndex;
    private int profit;


    // TODO create additional constructor for games with options,
    //  such as multiple players, different starting stacks etc.
    public Game(int betAmount, int chipStack) {
        Hand playerHand = new Hand();
        dealerHand = new Hand();
        playerHands = new ArrayList<Hand>();
        playerHands.add(playerHand);
        gameInProgress = true;
        playersTurn = true;
        currentHandIndex = 0;
        profit = 0;
        this.betAmount = betAmount;
        this.chipStack = chipStack;
    }

    // Copy constructor
    public Game(Game gameCopy) {
        this.playerHands = gameCopy.playerHands;
        this.dealerHand = gameCopy.dealerHand;
        this.gameInProgress = gameCopy.gameInProgress;
        this.playersTurn = gameCopy.playersTurn;
        this.betAmount = gameCopy.betAmount;
        this.chipStack = gameCopy.chipStack;
        this.currentHandIndex = gameCopy.currentHandIndex;
        this.profit = gameCopy.profit;
    }

    public Hand getPlayerHand(int index) {
        return playerHands.get(index);
    }

    public ArrayList<Hand> getPlayerHands() {
        return playerHands;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public int getChipStack() {
        return chipStack;
    }

    public int getProfit() {
        return profit;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public int getCurrentHandIndex() {
        return currentHandIndex;
    }

    public boolean isLastPlayerHand(int index) {
        return (index + 1) == playerHands.size();
    }

    public void setPlayerHand(Hand playerHand, int index) {
        this.playerHands.set(index, playerHand);
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    public void setChipStack(int chipStack) {
        this.chipStack = chipStack;
    }

    public boolean playerCanSplit(Hand hand) {
        // If hand size is exactly 2, and the two cards have the same value (e.g. are a pair,
        // allow them to be split.
        Card firstCardDealt = hand.getCardByPosition(0);
        Card secondCardDealt = hand.getCardByPosition(1);

        if (hand.getHandSize() == 2) {
            if (firstCardDealt.getName().equals(secondCardDealt.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean canAffordToSplitOrDouble() {
        return (chipStack - betAmount) > 0;
    }

    public void splitCards(int index) {
        Hand hand = playerHands.get(index);
        Card firstCardDealt = hand.getCardByPosition(0);
        Card secondCardDealt = hand.getCardByPosition(1);

        Hand firstSplit = new Hand();
        Hand secondSplit = new Hand();

        firstSplit.addCardToHand(firstCardDealt);
        secondSplit.addCardToHand(secondCardDealt);

        playerHands.set(index, firstSplit);
        playerHands.add(index + 1, secondSplit);
}

    public void nextHand(int index, HandStatus handStatus) {
        Hand hand = getPlayerHand(index);
        if (hand.getHandStatus() == null) {
            hand.setHandStatus(handStatus);
        }
        setPlayerHand(hand, index);

        if (!isLastPlayerHand(index)) {
            currentHandIndex =+ 1;
        } else {
            playersTurn = false;
        }
    }

    public void dealCardToPlayer(Card card, int index) {
        Hand hand = playerHands.get(index);
        hand.addCardToHand(card);
        playerHands.set(index, hand);
    }


    public void dealCardToDealer(Card card) {
        dealerHand.addCardToHand(card);
    }

    public void addToProfit(int amount) {
        profit += amount;
    }
}
