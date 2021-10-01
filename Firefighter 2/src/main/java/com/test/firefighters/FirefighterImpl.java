package com.test.firefighters;

import com.test.api.CityNode;
import com.test.api.Firefighter;
public class FirefighterImpl implements Firefighter {

  private CityNode currentPosition;
  private CityNode initialPosition;
  private int distance;

  public FirefighterImpl(CityNode initialPosition) {
    this.initialPosition = initialPosition;
    this.currentPosition = initialPosition;
  }

  @Override
  public CityNode getLocation() {
    return this.currentPosition;
  }

  @Override
  public void moveFireFighter(CityNode newPosition) {
    this.currentPosition = newPosition;
  }

  @Override
  public int distanceTraveled() {
    return Math.abs(currentPosition.getX() - initialPosition.getX()) + Math.abs(currentPosition.getY()) - initialPosition.getY();
  }

  public int distanceFarAway(CityNode cityNode) {
    return Math.abs(currentPosition.getX() - cityNode.getX()) + Math.abs(currentPosition.getY()) - cityNode.getY();
  }
}
