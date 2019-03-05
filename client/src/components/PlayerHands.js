import React from 'react'
import GameContext from '../context/game-context';
import Hand from './Hands/Hand';
import PlayerScore from './Hands/PlayerScore';

export default function PlayerHands() {
    return (
        <GameContext.Consumer>
            {context => (
                <div className="player-hands">
                    {context.playerHands ? context.playerHands.map((hand, i) =>
                        <div key={i}>
                                <Hand hands={hand.hand}/>
                                <PlayerScore position={i} />
                            </div>
                    ) : ''}
                </div>
            )}
        </GameContext.Consumer>
    )
}
