package com.test.impls;

import com.test.api.CityNode;

public class FireStation extends BuildingImpl {

  public FireStation(CityNode location) {
    super(location, true);
  }
}
