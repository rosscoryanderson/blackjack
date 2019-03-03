import React from 'react'
import GameContext from '../../context/game-context'


export default function TestGames() {
    return (
        <GameContext.Consumer>
            {context => (
                <button className="btn btn-hit" onClick={context.hitAction}>Hit</button>
            )}
        </GameContext.Consumer>
    );
}

