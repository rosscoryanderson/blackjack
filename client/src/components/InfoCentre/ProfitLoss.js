import React from 'react'
import GameContext from '../../context/game-context'

export default function ProfitLoss() {
  return (
    <GameContext.Consumer>
        {context => (
            <h1 className="bet-amount">Bet Amount: {context.betAmount} / Chip Stack: {context.chipStack} / Profit: {context.profit}</h1>
        )}
    </GameContext.Consumer>
  )
}
