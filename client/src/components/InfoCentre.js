import React, { Component } from 'react'
import ProfitLoss from './InfoCentre/ProfitLoss'
import GameContext from '../context/game-context'

export default class InfoCentre extends Component {
    displayToast(amount) {
        if (amount < 0) {
            return <h1 className="">Dealer Wins (<span className="score-invalid">${amount}</span>)</h1>
        } else if (amount == 0) {
            return <h1 className="">Push (<span className="score-neutral">${amount}</span>)</h1>
        } else {
            return <h1 className="">You Win! (<span className="score-valid">${amount}</span>)</h1>
        }
    }


    render() {
        return (
            <GameContext.Consumer>
                {context => (
                    <div className="info-centre">
                        {!context.gameInProgress && context.dealerHand !== null ? this.displayToast(context.profit) : <ProfitLoss />}
                    </div>
                )}
            </GameContext.Consumer>
        )
    }
}
