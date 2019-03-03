import React from 'react'
import GameContext from '../context/game-context';
import Hand from './Hand';
import DealerScore from './DealerScore'

export default function DealerHand() {
    return (
        <GameContext.Consumer>
            {context => (
                <div className="dealer-hand">
                    {context.dealerHand && context.dealerHand.hand ? 
                        <div>
                            <Hand hands={context.dealerHand.hand} />
                            <DealerScore />
                        </div> : ''}
                </div>
            )}
        </GameContext.Consumer>
    )
}
