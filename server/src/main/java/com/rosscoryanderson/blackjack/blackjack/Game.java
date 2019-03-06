package com.rosscoryanderson.blackjack.blackjack;

import java.util.ArrayList;

public class Game {
    // A game manages a single hand of BlackJack, while a match continues until the player is broke.
    private ArrayList<Hand> playerHands;
    private Hand dealerHand;
    private boolean gameInProgress;
    private boolean playersTurn;
    private int currentHandIndex;

    public Game() {
        Hand playerHand = new Hand();
        dealerHand = new Hand();
        playerHands = new ArrayList<Hand>();
        playerHands.add(playerHand);
        gameInProgress = true;
        playersTurn = true;
        currentHandIndex = 0;
    }

    public Hand getPlayerHand() {
        return playerHands.get(currentHandIndex);
    }

    public ArrayList<Hand> getPlayerHands() {
        return playerHands;
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public int getCurrentHandIndex() {
        return currentHandIndex;
    }

    public boolean isLastPlayerHand() {
        return (currentHandIndex + 1) == playerHands.size();
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHands.set(currentHandIndex, playerHand);
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    public void splitCards() {
        Hand hand = playerHands.get(currentHandIndex);
        Card firstCardDealt = hand.getCardByPosition(0);
        Card secondCardDealt = hand.getCardByPosition(1);

        Hand firstSplit = new Hand();
        Hand secondSplit = new Hand();

        firstSplit.addCardToHand(firstCardDealt);
        secondSplit.addCardToHand(secondCardDealt);

        playerHands.set(currentHandIndex, firstSplit);
        playerHands.add(currentHandIndex + 1, secondSplit);
}

    public void nextHand(HandStatus handStatus) {
        Hand hand = getPlayerHand();
        if (hand.getHandStatus() == null) {
            hand.setHandStatus(handStatus);
            setPlayerHand(hand);
            if (!isLastPlayerHand()) {
                currentHandIndex =+ 1;
            } else {
                playersTurn = false;
            }
        }
    }

    public void dealCardToPlayer(Card card) {
        Hand hand = playerHands.get(currentHandIndex);
        hand.addCardToHand(card);
        playerHands.set(currentHandIndex, hand);
    }


    public void dealCardToDealer(Card card) {
        dealerHand.addCardToHand(card);
    }


}
