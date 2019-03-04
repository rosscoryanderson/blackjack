import React from 'react'
import Card from './Hands/Card'

export default function Hand(props) {
    return (
        <div className="imageContainer">
            {props.hands ? props.hands.map((card, i) => <Card key={i} value={card.name} suit={card.suit} />) : ''}
        </div>
    )
}
