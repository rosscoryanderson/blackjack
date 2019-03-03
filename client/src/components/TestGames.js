import React from 'react'
import GameContext from '../context/game-context'

export default function TestGames() {
    return (
        <GameContext.Consumer>
            {context => (
                <div>
                    <input type="number"></input>
                    <button onClick={context.createNewGame}>Create New Game</button>
                    <button onClick={context.createNewMatch}>Reset Match</button>
                </div>
            )}
        </GameContext.Consumer>
    );
}

