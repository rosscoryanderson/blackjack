package com.rosscoryanderson.blackjack.blackjack;

public class Game {
    // A game manages a single hand of BlackJack, while a match continues until the player is broke.

    private Hand playerHand;
    private Hand dealerHand;

    // Hand if the player splits
    private Hand playerHandSplit;
    private boolean split;

    private GameStatus gameStatus;
    private boolean playersTurn;
    private boolean doubleDown;
    private boolean doubleDownSplit;
    private int betAmount;
    private int chipStack;



    // TODO create additional constructor for games with options,
    //  such as multiple players, different starting stacks etc.
    public Game(int betAmount, int chipStack) {
        playerHand = new Hand();
        dealerHand = new Hand();
        split = false;
        gameStatus = GameStatus.IN_PROGRESS;
        playersTurn = true;
        doubleDown = false;
        doubleDownSplit = false;
        this.betAmount = betAmount;
        this.chipStack = chipStack;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getPlayerHandSplit() {
        return playerHandSplit;
    }

    public boolean isSplit() {
        return split;
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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isDoubleDown() {
        return doubleDown;
    }

    public boolean isDoubleDownSplit() {
        return doubleDownSplit;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public void setPlayerHandSplit(Hand playerHandSplit) {
        this.playerHandSplit = playerHandSplit;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setDoubleDown(boolean doubledDown) {
        this.doubleDown = doubledDown;
    }

    public void setDoubleDownSplit(boolean doubleDownSplit) {
        this.doubleDownSplit = doubleDownSplit;
    }

    public boolean playerCanSplit(Card firstCardDealt, Card secondCardDealt) {
        // If hand size is exactly 2, and the two cards have the same value (e.g. are a pair,
        // allow them to be split.
        if (playerHand.getHandSize() == 2) {
            if (firstCardDealt.getName().equals(secondCardDealt.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean canAffordToSplitOrDouble() {
        return (chipStack - betAmount) > 0;
    }

    public void splitCards() {
        Card firstCardDealt = playerHand.getCardByPosition(0);
        Card secondCardDealt = playerHand.getCardByPosition(1);

        if (playerCanSplit(firstCardDealt, secondCardDealt) && canAffordToSplitOrDouble()) {
            split = true;
            betAmount = betAmount * 2;

            Hand firstSplit = new Hand();
            Hand secondSplit = new Hand();

            firstSplit.addCardToHand(firstCardDealt);
            secondSplit.addCardToHand(secondCardDealt);

            setPlayerHand(firstSplit);
            setPlayerHandSplit(secondSplit);
        }
    }

    // In the case that the players hand busted or got Black Jack,
    // but their split hand is still active, we can swap it back to
    // the main hand to prevent having to do too many checks.
    public void removeSplit(Hand firstSplit, Hand secondSplit) {
        playerHand = secondSplit;
        playerHandSplit = null;
        split = false;
    }

    public void dealCardToPlayer(Card card) {
        playerHand.addCardToHand(card);
    }

    public void dealCardToPlayerSplit(Card card) {
        playerHandSplit.addCardToHand(card);
    }

    public void dealCardToDealer(Card card) {
        dealerHand.addCardToHand(card);
    }


}
