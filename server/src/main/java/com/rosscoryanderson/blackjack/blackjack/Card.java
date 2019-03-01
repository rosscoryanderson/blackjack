package com.rosscoryanderson.blackjack.blackjack;

public class Card {
    Suit suit;
    Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue() {
        return value.getValue();
    }

    public boolean isAce() {
        return value.getName() == "Ace";
    }
}
