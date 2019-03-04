import React, { Component } from 'react'
import GameContext from '../../context/game-context'

export default class DealerScore extends Component {
    static contextType = GameContext;

    displayScore() {
        if (this.context.dealerHand) {
            if(this.context.dealerHand.handValue > 21) {
                return <h4 className="score-display score-invalid">{this.context.dealerHand.largestHandValue}</h4>
            } else {
                return <h4 className="score-display score-valid">{this.context.dealerHand.largestHandValue}</h4>
            }
        }
    }
    render() {
        return (
            <div>
                { this.displayScore() }
                { console.log("display score") }
                { console.log(this.displayScore()) }
            </div>
        )
    }
}