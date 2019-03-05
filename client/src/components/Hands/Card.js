import React from 'react'

export default function Card(props) {
    return (
        <div className="imageContainer">
            <img src={require('../../cards/' + props.value.toLowerCase() +'_of_' + props.suit.toLowerCase() +'.svg')} className="cardImage " />
            {console.log(props.isActive)}
        </div>
            )
          }
