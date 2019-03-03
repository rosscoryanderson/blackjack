import React from 'react'
import HitButton from './ActionButtons/HitButton';
import StandButton from './ActionButtons/StandButton';
import SplitButton from './ActionButtons/SplitButton';
import DoubleDownButton from './ActionButtons/DoubleDownButton';

export default function ActionButtonContainer() {
  return (
    <div className="action-button-container">
      <HitButton />
      <StandButton />
      <SplitButton />
      <DoubleDownButton />
    </div>
  )
}
