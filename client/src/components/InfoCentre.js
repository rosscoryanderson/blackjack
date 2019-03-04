import React, { Component } from 'react'
import ProfitLoss from './InfoCentre/ProfitLoss';

export default class InfoCentre extends Component {
  render() {
    return (
      <div className="info-centre">
        <ProfitLoss />
      </div>
    )
  }
}
