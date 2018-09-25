//package edu.nd.sarec.railwaycrossing.model.infrastructure;
package railwaycrossing.model.infrastructure;

import java.awt.Point;
import java.util.Collection;
import java.util.Vector;

//import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
//import edu.nd.sarec.railwaycrossing.model.vehicles.CarFactory;
import railwaycrossing.model.infrastructure.gate.CrossingGate;
import railwaycrossing.model.vehicles.CarFactory;

/**
 * Represents a single road
 * @author jane
 *
 */
public class Road {
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	private CarFactory carFactory;
	Direction direction;
	Collection<CrossingGate> gates;
	boolean clearEnds = false;
	int roadSize;

	public Road(){}

	public Road(Point start, Point end, Direction direction, boolean buildCarFactory, boolean clearEnds){
		startX = start.x;
		startY = start.y;
		endX = end.x;
		endY = end.y;
		roadSize = 18;

		this.direction = direction;
		gates = new Vector<CrossingGate>();
		this.clearEnds = clearEnds;

	}

	public void addCFObserver(CarFactory newCF) {
		this.carFactory.addObserver(newCF);
	}

	// Adds a gate to a road
	// In case a new gate is added after the factory is assigned, we reassign factory
	// The factory needs to know all gates on the road in order to register each car as an observer.
	public void assignGate(CrossingGate gate, CrossingGate oG, CarFactory cF){
		gates.add(gate);
		if (carFactory != null)
			carFactory = new CarFactory(direction, new Point(startX-roadSize/2,startY), gates, oG, cF);  // allows additional gates.  Needs fixing
	}

	public void addCarFactory(CrossingGate oG, CarFactory cF){
		if (carFactory == null) // We only allow one
			if(this.direction == Direction.SOUTH)
				carFactory = new CarFactory(this.direction, new Point(startX-roadSize/2,startY), gates, oG, cF);
			if(this.direction == Direction.WEST)
				carFactory = new CarFactory(this.direction, new Point(endX,startY -roadSize/2), gates, oG, null);
	}

	public CarFactory getCarFactory(){
		return carFactory;
	}

	public int getStartX(){
		return startX;
	}

	public int getEndX(){
		return endX;
	}

	public int getStartY(){
		return startY;
	}

	public int getEndY(){
		return endY;
	}

	public Direction getDirection(){
		return direction;
	}

	public boolean getClearEnds(){
		return clearEnds;
	}

	public int getRoadWidth(){
		return roadSize;
	}
}