import React, { Component } from 'react';
import './App.css';
import Hand from './components/Hand';
import ActionCentre from './components/ActionCentre';
import GameContext from './context/game-context';
import TestGames from './components/TestGames';
import axios from 'axios';
import DealerHand from './components/DealerHand';
import PlayerHands from './components/PlayerHands';

const SERVER = "http://localhost:8080/api/blackjack/"

class App extends Component {

  state = {
    dealerHand: null,
    gameInProgress: false,
    playersTurn: false,
    betAmount: 5,
    currentHandIndex: 0,
    profit: 0,
    chipStack: 0,
    playerHands: [{
      canSplit: false,
      canDoubleDown: false
    }],
  };

  createNewMatch = () => {
    //Services.sendNewMatchRequest();
    axios.post(SERVER, {})
      .then(res => {
        this.setState({
          dealerHand: null,
          gameInProgress: false,
          playersTurn: false,
          betAmount: 5,
          currentHandIndex: 0,
          profit: 0,
          chipStack: 100,
          playerHands: [{
            canSplit: false,
            canDoubleDown: false
          }],
        })
      })
  };

  createNewGame = betAmount => {
    //Services.sendNewGameRequest(5);
    axios.post(SERVER + "newGame/" + betAmount, {})
      .then(res => {
        console.log("new game created");
        this.mapResponseToState(res.data);
        console.log(this.state)
      })
  };

  hitAction = () => {
    //Services.sendHitAction(this.state.currentHandIndex);
    axios.post(SERVER + "hit/" + this.state.currentHandIndex, {})
      .then(res => {
        this.mapResponseToState(res.data);
        console.log('State after hit')
        console.log(this.state)
      })
  };

  standAction = handIndex => {
    //Services.sendStandAction(this.state.currentHandIndex);
    axios.post(SERVER + "stand/" + this.state.currentHandIndex, {})
      .then(res => {
        this.mapResponseToState(res.data);
        console.log('State after stand')
        console.log(this.state)
      })
  };

  splitAction = handIndex => {
    //Services.sendSplitAction(this.state.currentHandIndex);
    axios.post(SERVER + "split/" + this.state.currentHandIndex, {})
      .then(res => {
        this.mapResponseToState(res.data);
        console.log('State after split')
        console.log(this.state)
      })
  };

  doubleDownAction = handIndex => {
    axios.post(SERVER + "doubleDown/" + this.state.currentHandIndex, {})
      .then(res => {
        this.mapResponseToState(res.data);
        console.log('State after double down')
        console.log(this.state)
      })
  };

  increaseBetAmount() {
    console.log('increase bet called')
    if ((this.state.betAmount + 5) < this.state.chipStack) {
      console.log('if bet called')
      const newValue = this.state.betAmount + 5;
      this.setState({ betAmount: newValue })
      this.betAmount += 5;
    }
  }

  mapResponseToState = (res) => {
    this.setState({
      dealerHand: res.dealerHand,
      gameInProgress: res.gameInProgress,
      playersTurn: res.playersTurn,
      betAmount: res.betAmount,
      chipStack: res.chipStack,
      currentHandIndex: res.currentHandIndex,
      profit: res.profit,
      playerHands: res.playerHands,
    })
  }

  render() {
    return (
      <GameContext.Provider value={{
        dealerHand: this.state.dealerHand,
        gameInProgress: this.state.gameInProgress,
        playersTurn: this.state.playersTurn,
        betAmount: this.state.betAmount,
        currentHandIndex: this.state.currentHandIndex,
        profit: this.state.profit,
        chipStack: this.state.chipStack,
        playerHands: this.state.playerHands,
        createNewGame: this.createNewGame,
        createNewMatch: this.createNewMatch,
        hitAction: this.hitAction,
        standAction: this.standAction,
        splitAction: this.splitAction,
        doubleDownAction: this.doubleDownAction,
        increaseBetAmount: this.increaseBetAmount
      }}>
        <div className="App">
          <div className="table">
            <h1>Hello World</h1>
            <DealerHand />
            <PlayerHands />
          </div>
          <div className="container2"></div>
          <ActionCentre />
          <TestGames />
        </div>
      </GameContext.Provider>
    );
  }
}

export default App;
