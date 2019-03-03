import React from 'react';

React.createContext({
    dealerHand: {
        hand: [
            {
                suit: "DIAMONDS",
                value: 10,
                name: "King"
            }
        ],
        handValue: 10,
        aceInHand: false,
        doubleDown: false,
        handStatus: null,
        largestHandValue: 10,
        handSize: 1, 
        canSplit: false,
        canDoubledown: false
    },
    gameInProgress: true,
    playersTurn: true,
    betAmount: 5,
    currentHandIndex: 0,
    profit: 0,
    allPlayerHands: [
        {
            hand: [
                {
                    suit: "HEARTS",
                    value: 10,
                    name: "Ten"
                },
                {
                    suit: "SPADES",
                    value: 10,
                    name: "Jack"
                }
            ],
            handValue: 20,
            aceInHand: false,
            doubleDown: false,
            handStatus: null,
            largestHandValue: 20,
            handSize: 2, 
            canSplit: false,
            canDoubledown: false
        }
    ]
})