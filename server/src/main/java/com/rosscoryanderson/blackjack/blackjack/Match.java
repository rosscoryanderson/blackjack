package com.rosscoryanderson.blackjack.blackjack;

public class Match {
    // A match is a series of games, running until the player goes broke.
    // It manages the running total of chips and the shoe.

    // Default player chips to $100.
    private int playerChipStack = 100;
    private Shoe shoe;

    // TODO: Add match options
    public Match() {
        shoe = new Shoe();
    }

    public Game newGame(int betAmount) {
        playerChipStack = playerChipStack - betAmount;
        Game game = new Game(betAmount, playerChipStack);
        dealCardToPlayer(game);
        dealCardToDealer(game);
        dealCardToPlayer(game);

        return game;
    }

    public int getPlayerChipStack() {
        return playerChipStack;
    }

    public void setPlayerChipStack(int playerChipStack) {
        this.playerChipStack = playerChipStack;
    }

    public void dealCardToPlayer(Game game) {
        game.dealCardToPlayer(shoe.drawCard());
    }

    public void dealCardToPlayerSplit(Game game) {
        game.dealCardToPlayerSplit(shoe.drawCard());
    }

    public void dealCardToDealer(Game game) {
        game.dealCardToDealer(shoe.drawCard());
    }



}
