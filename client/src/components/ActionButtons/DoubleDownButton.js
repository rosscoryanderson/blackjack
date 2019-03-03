import React from 'react'
import GameContext from '../../context/game-context'


export default function DoubleDownButton() {
    return (
        <GameContext.Consumer>
            {context => (
                <button 
                    className="btn btn-double-down" 
                    onClick={context.doubleDownAction}>
                        Double Down
                </button>
            )}
        </GameContext.Consumer>
    );
}