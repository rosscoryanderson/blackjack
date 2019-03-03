import React from 'react'
import GameContext from '../../context/game-context'


export default function SplitButton() {
    return (
        <GameContext.Consumer>
            {context => (
                <button className="btn btn-split" onClick={context.splitAction}>Split</button>
            )}
        </GameContext.Consumer>
    );
}