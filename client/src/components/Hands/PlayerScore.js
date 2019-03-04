import React, { Component } from 'react'
import GameContext from '../../context/game-context'

export default class PlayerScore extends Component {
    static contextType = GameContext;

    displayScore() {
        if (this.context.playerHands[0].hand) {
            if (this.context.playerHands[this.props.position].largestHandValue > this.context.playerHands[this.props.position].handValue) {
                return <div>
                    <h4 className="score-display score-valid">{this.context.playerHands[this.props.position].handValue}</h4>
                    <h4 className="score-display score-neutral"> / </h4>
                    <h4 className="score-display score-valid">{this.context.playerHands[this.props.position].largestHandValue}</h4>
                </div>
            } else if(this.context.playerHands[this.props.position].handValue > 21) {
                return <h4 className="score-display score-invalid">{this.context.playerHands[this.props.position].handValue}</h4>
            } else {
                return <h4 className="score-display score-valid">{this.context.playerHands[this.props.position].handValue}</h4>
            }
        }
    }
    render() {
        return (
            <div>
                { this.displayScore() }
            </div>
        )
    }
}


