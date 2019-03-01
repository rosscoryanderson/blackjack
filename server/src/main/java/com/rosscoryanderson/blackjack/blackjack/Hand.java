package com.rosscoryanderson.blackjack.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    ArrayList<Card> hand;
    int handValue;
    boolean handHasAce;

    public Hand() {
        hand = new ArrayList<Card>();
        handValue = 0;
        handHasAce = false;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
        handValue += card.getValue();
        handHasAce = card.isAce();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getHandValue() {
        return handValue;
    }

    public boolean handHasAce() {
        return handHasAce;
    }
}
