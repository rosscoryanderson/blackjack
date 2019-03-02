package com.rosscoryanderson.blackjack.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> hand;
    private int handValue;
    private boolean aceInHand;

    public Hand() {
        hand = new ArrayList<Card>();
        handValue = 0;
        aceInHand = false;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
        handValue += card.getValue();
        if(card.getName() == "Ace" && !aceInHand) {
            aceInHand = true;
        }
    }

    public int getHandSize() {
        return hand.size();
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getHandValue() {
        return handValue;
    }

    public boolean isAceInHand() {
        return aceInHand;
    }

    public Card getCardByPosition(int position) {
        return hand.get(position);
    }


}
