import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Hand from './components/Hand';
import ActionButtonContainer from './components/ActionButtonContainer';
import GameContext from './context/game-context';
import Services from './services/services';

class App extends Component {
  state = {
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
  };

  resetMatch = () => { console.log("Reset Match Activated") };
  createNewGame = betAmount => { console.log("Create New Game Activated") };
  hitAction = () => { 
    //console.log("Hit Action Activated") 
    Services.sendNewGameRequest(5)
    Services.sendHitAction(this.state.currentHandIndex);
  };
  standAction = handIndex => { console.log("Stand Action Activated") };
  splitAction = handIndex => { console.log("Split Action Activated") };
  doubleDownAction = handIndex => { console.log("Double Down Action Activated") };

  render() {
    return (
      <GameContext.Provider value={{
        dealerHand: this.state.dealerHand,
        gameInProgress: this.state.gameInProgress,
        playersTurn: this.state.playersTurn,
        betAmount: this.state.betAmount,
        currentHandIndex: this.state.currentHandIndex,
        profit: this.state.profit,
        allPlayerHands: this.state.allPlayerHands,
        createNewGame: this.createNewGame,
        hitAction: this.hitAction,
        standAction: this.standAction,
        splitAction: this.splitAction,
        doubleDownAction: this.doubleDownAction
      }}>
        <div className="App">
          <div className="table">
            <h1>Hello World</h1>
            <Hand />
          </div>
          <div className="container2"></div>
          <ActionButtonContainer />
        </div>
      </GameContext.Provider>
    );
  }
}

export default App;
