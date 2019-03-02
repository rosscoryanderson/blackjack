package com.rosscoryanderson.blackjack.blackjack;

import java.util.Stack;

public class Shoe {
    // Default to 6 decks in a shoe.
    private int numberOfDecksInShoe = 6;
    private Stack<Card> shoe;


    public Shoe() {
        shoe = createShoe();
    }

    public Shoe(int numberOfDecksInShoe) {
        this.numberOfDecksInShoe = numberOfDecksInShoe;
        shoe = createShoe();
    }

    private Stack<Card> createShoe() {
        Stack<Card> shoeCreate = new Stack<Card>();

        for(int i = 0; i < numberOfDecksInShoe; i++) {
            Deck deck = new Deck();
            int size = deck.getDeckSize();

            for(int j = 0; j < size; j++) {
                shoeCreate.push(deck.getCard());
            }
        }
        return shoeCreate;
    }

    public Card drawCard() {
        if(shoe.empty()) {
            shoe = createShoe();
        }
        return shoe.pop();
    }

    public int getNumberOfCardsInShoe(){
        return shoe.size();
    }

    // This will need to be edited if we want to include decks with different numbers of cards.
    public int getTotalCardsInShoe() {
        return numberOfDecksInShoe * 52;
    }
}
