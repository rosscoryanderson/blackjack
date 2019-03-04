import React from 'react'
import GameContext from '../../context/game-context'

export default function StartNewMatch() {
    return (
        <GameContext.Consumer>
            {context => (
                <button className="btn new-match" onClick={context.createNewMatch}>Start New Match</button>
            )}
        </GameContext.Consumer>
    )
}
