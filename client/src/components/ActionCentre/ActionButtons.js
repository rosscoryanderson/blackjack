import React from 'react'
import HitButton from '../ActionButtons/HitButton';
import StandButton from '../ActionButtons/StandButton';
import SplitButton from '../ActionButtons/SplitButton';
import DoubleDownButton from '../ActionButtons/DoubleDownButton';
import GameContext from '../../context/game-context'

export default function ActionButtons() {
  return (
    <GameContext.Consumer>
      {context => (
        <div>
          {context.gameInProgress && <div>
            <HitButton />
            <StandButton />
            {context.playerHands[context.currentHandIndex].canSplit &&
              context.betAmount <= context.chipStack &&
              <SplitButton />}
            {context.playerHands[context.currentHandIndex].canDoubleDown &&
              context.betAmount <= context.chipStack &&
              <DoubleDownButton />}
          </div>}
        </div>
      )}
    </GameContext.Consumer>

  )
}
