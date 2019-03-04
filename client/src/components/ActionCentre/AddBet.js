import React, { Component } from 'react'
import GameContext from '../../context/game-context'

export default class AddBet extends Component {
    static contextType = GameContext

    state = {
        betAmount: this.context.betAmount,
        chipStack: this.context.chipStack
    }

    betAmount = this.context.betAmount;
    chipStack = this.context.chipStack;

    increaseBetAmount() {
        if ((this.state.betAmount + 5) <= this.state.chipStack) {
            const newValue = this.state.betAmount + 5;
            this.setState({
                betAmount: newValue
            })
        }
    }

    decreaseBetAmount() {
        if ((this.state.betAmount - 5) >= 5) {
            const newValue = this.state.betAmount - 5;
            this.setState({
                betAmount: newValue
            })
        }
    }

    recalculateBetSize() {
        if (this.state.betAmount > this.state.chipStack) {
            this.setState({
                betAmount: this.state.chipStack
            })
        }
    }

    componentDidMount() {
        this.recalculateBetSize()
    }

    render() {
        return (
            <div>
                <button
                    className="bet-btn lower-bet"
                    disabled={this.state.betAmount === 5 ? "disabled" : false}
                    onClick={() => this.decreaseBetAmount()}>-</button>
                <h3 className="bet-amount">{ this.state.betAmount }</h3>
                <button
                    className="bet-btn increase-bet"
                    disabled={(this.state.betAmount + 5) > this.state.chipStack ? "disabled" : false}
                    onClick={() => this.increaseBetAmount()}>+</button>
                <button
                    className="btn make-bet"
                    onClick={() => this.context.createNewGame(this.state.betAmount)}>Place Bet</button>
            </div>
        )
    }
}
