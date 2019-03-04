import React from 'react'
import GameContext from '../context/game-context'
import ActionButtons from './ActionCentre/ActionButtons';
import AddBet from './ActionCentre/AddBet';
import StartNewMatch from './ActionCentre/StartNewMatch';

export default function ActionCentre() {
    return (
        <GameContext.Consumer>
            {context => (
                <div className="action-button-container">
                    {context.gameInProgress ? <ActionButtons /> : 
                        context.chipStack === 0 ? 
                            <StartNewMatch /> :
                            <AddBet />}
                </div>
            )}
        </GameContext.Consumer>
    )
}
