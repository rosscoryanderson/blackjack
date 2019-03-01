package com.rosscoryanderson.blackjack.blackjack;

import java.util.Stack;

public class Shoe {
    // Default to 6 decks in a shoe.
    private int numberOfDecksInShoe = 6;
    private Stack<Card> shoe;


    public Shoe() {
        shoe = new Stack<Card>();
    }

    public Shoe(int numberOfDecksInShoe) {
        this.numberOfDecksInShoe = numberOfDecksInShoe;
        shoe = new Stack<Card>();
    }

    private Stack<Card> createShoe() {
        Stack<Card> shoeCreate = new Stack<Card>();

        for(int i = 0; i < numberOfDecksInShoe; i++) {
            Deck deck = new Deck();
            int size = deck.getDeckSize();

            for(int j = 0; j < 52; j++) {
                shoeCreate.push(deck.getCard());
            }
        }
        return shoeCreate;
    }

    public Card drawCard() {
        return shoe.pop();
    }

    public int getNumberOfCardsInShoe(){
        return shoe.size();
    }

    public int getTotalCardsInShoe() {
        return numberOfDecksInShoe * 52;
    }
}
