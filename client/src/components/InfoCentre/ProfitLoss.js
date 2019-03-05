import React from 'react'
import GameContext from '../../context/game-context'

export default function ProfitLoss() {
  return (
    <GameContext.Consumer>
        {context => (
            <h1 className="info-center-text">Bet Amount: {context.betAmount} / Chip Stack: {context.chipStack}</h1>
        )}
    </GameContext.Consumer>
  )
}
