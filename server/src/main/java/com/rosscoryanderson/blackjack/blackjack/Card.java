package com.rosscoryanderson.blackjack.blackjack;

public class Card {
    private Suit suit;
    private Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue() {
        return value.getValue();
    }

    public String getName() {
        return value.getName();
    }

    public String getSuit() {
        return suit.toString();
    }

}
