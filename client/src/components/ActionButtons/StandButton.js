import React from 'react'
import GameContext from '../../context/game-context'


export default function StandButton() {
    return (
        <GameContext.Consumer>
            {context => (
                <button className="btn btn-stand" onClick={context.standAction}>Stand</button>
            )}
        </GameContext.Consumer>
    );
}
