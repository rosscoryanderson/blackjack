import React from 'react'
import HitButton from './ActionButtons/HitButton';
import StandButton from './ActionButtons/StandButton';
import SplitButton from './ActionButtons/SplitButton';
import DoubleDownButton from './ActionButtons/DoubleDownButton';
import GameContext from '../context/game-context'

export default function ActionButtonContainer() {
  return (
    <GameContext.Consumer>
      {context => (
        <div className="action-button-container">
          {context.gameInProgress && <div>
            <HitButton />
            <StandButton />
            {context.playerHands[context.currentHandIndex].canSplit && <SplitButton />}
            {context.playerHands[context.currentHandIndex].canDoubleDown && <DoubleDownButton />}
          </div>}
        </div>
      )}
    </GameContext.Consumer>

  )
}
