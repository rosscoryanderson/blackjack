package com.rosscoryanderson.blackjack.blackjack;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Deck {
    private Stack<Card> deck;

    public Deck() {
        deck = shuffledDeck();
    }

    private ArrayList<Card> createSortedDeck() {
        ArrayList<Card> cards = new ArrayList<Card>();

        for (Suit suit: Suit.values()) {
            for (Value value: Value.values()) {
                Card card = new Card(suit, value);
                cards.add(card);
            }
        }

        return cards;
    }

    private Stack<Card> shuffledDeck() {
        ArrayList<Card> sortedDeck = createSortedDeck();
        Stack<Card> shuffledDeck = new Stack<Card>();
        Random random = new Random();

        for (int i = 0; i < 52; i++) {
            int size = sortedDeck.size();
            int position = random.nextInt(size);
            Card card = sortedDeck.remove(position);

            shuffledDeck.push(card);
        }

        return shuffledDeck;
    }

    public Card getCard(){
        return deck.pop();
    }

    public int getDeckSize() {
        return deck.size();
    }
}
