package com.test.firefighters;

import com.test.api.City;
import com.test.api.CityNode;
import com.test.api.FireDispatch;
import com.test.api.Firefighter;
import com.test.api.exceptions.NoFireFoundException;

import java.util.*;

public class FireDispatchImpl implements FireDispatch {

    private City city;
    private List<Firefighter> firefighters;

    public FireDispatchImpl(City city) {
        this.city = city;
    }

    @Override
    public void setFirefighters(int numFirefighters) {
        this.firefighters = new ArrayList<>();
        for (int i = 0; i< numFirefighters; i++) {
            this.firefighters.add(new FirefighterImpl(this.city.getFireStation().getLocation()));
        }
    }

    @Override
    public List<Firefighter> getFirefighters() {
        return this.firefighters;
    }

    public Firefighter getClosestFireFighter(CityNode cityNode) {
        Firefighter closestFF = this.firefighters.get(0);
        for(Firefighter ff: this.firefighters) {
            if(ff.distanceFarAway(cityNode) < closestFF.distanceFarAway(cityNode)) closestFF = ff;
        }
        return closestFF;
    }

    @Override
    public void dispatchFirefighers(CityNode... burningBuildings) {

        boolean visited[][] = new boolean[this.city.getXDimension()][this.city.getYDimension()];
        CityNode fireStation = this.city.getFireStation().getLocation();
        visited[fireStation.getX()][fireStation.getY()] = true;

        fireExtinguisher(visited, fireStation, Arrays.asList(burningBuildings));
    }

    private void fireExtinguisher(boolean visited[][], CityNode sourceBuilding, List<CityNode> burningBuildings) {

        LinkedList<CityNode> queue = new LinkedList<>();

        visited[sourceBuilding.getX()][sourceBuilding.getY()] = true;
        queue.add(sourceBuilding);

        while (!queue.isEmpty()) {

            CityNode pointerBuilding = queue.poll();
            System.out.print("Fire man at: " + pointerBuilding.getX() + " " + pointerBuilding.getY());

            if (burningBuildings.contains(pointerBuilding)) {

                try {
                    this.city.getBuilding(pointerBuilding).extinguishFire();
                } catch (NoFireFoundException e) {
                    e.printStackTrace();
                }
                getClosestFireFighter(pointerBuilding).moveFireFighter(pointerBuilding);
            }


            List<CityNode> neighbors = new ArrayList<>();

            neighbors.add(new CityNode(sourceBuilding.getX() + 1, sourceBuilding.getY()));
            neighbors.add(new CityNode(sourceBuilding.getX(), sourceBuilding.getY() + 1));
            neighbors.add(new CityNode(sourceBuilding.getX() - 1, sourceBuilding.getY()));
            neighbors.add(new CityNode(sourceBuilding.getX(), sourceBuilding.getY() - 1));
            neighbors.add(new CityNode(sourceBuilding.getX() + 1, sourceBuilding.getY() - 1));
            neighbors.add(new CityNode(sourceBuilding.getX() + 1, sourceBuilding.getY() + 1));
            neighbors.add(new CityNode(sourceBuilding.getX() - 1, sourceBuilding.getY() - 1));
            neighbors.add(new CityNode(sourceBuilding.getX() - 1, sourceBuilding.getY() + 1));

            for (CityNode n : neighbors) {
                if (n.getX() < this.city.getXDimension() && n.getX() >= 0
                    && (n.getY() < this.city.getYDimension() && n.getY() >= 0)
                    && !visited[n.getX()][n.getY()]) {
                    queue.add(n);
                    visited[n.getX()][n.getY()] = true;
                }
            }
        }
    }
}
