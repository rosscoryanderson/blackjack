package com.rosscoryanderson.blackjack.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> hand;
    private int handValue;
    private boolean aceInHand;
    private boolean doubleDown;
    private HandStatus handStatus;

    public Hand() {
        hand = new ArrayList<Card>();
        handValue = 0;
        aceInHand = false;
        doubleDown = false;
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

    public boolean isDoubleDown() {
        return doubleDown;
    }

    public HandStatus getHandStatus() {
        return handStatus;
    }

    public void setHandStatus(HandStatus handStatus) {
        this.handStatus = handStatus;
    }

    public void setDoubleDown(boolean doubleDown) {
        this.doubleDown = doubleDown;
    }

    public boolean isAceInHand() {
        return aceInHand;
    }

    public Card getCardByPosition(int position) {
        return hand.get(position);
    }

    public int getLargestHandValue() {
        if(aceInHand == true && (getHandValue() + 10) <= 21) {
            return getHandValue() + 10;
        } else {
            return getHandValue();
        }
    }


}
