import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Hand from './components/Hand';
import ActionButtonContainer from './components/ActionButtonContainer';


class App extends Component {
  render() {
    return (
      <div className="App">
        <div className="table">
          <h1>Hello World</h1>
          <Hand />
        </div>
        <div className="container2"></div>
        <ActionButtonContainer />
      </div>
    );
  }
}

export default App;
