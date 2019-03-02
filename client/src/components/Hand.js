import React from 'react'
import Card from './Card'

export default function Hand() {
    return (
        <div className="imageContainer">
            <Card value="two" suit="spades" />
            <Card value="ace" suit="SPADES" />
            <Card value="KiNg" suit="DIAMONDS2" />
        </div>
    )
}
