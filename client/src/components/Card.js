import React from 'react'

export default function Card(props) {
    return (
        <div>
            <img src={require('../cards/' + props.value.toLowerCase() +'_of_' + props.suit.toLowerCase() +'.svg')} />
        </div>
            )
          }
