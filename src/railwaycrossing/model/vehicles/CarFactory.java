//package edu.nd.sarec.railwaycrossing.model.vehicles;
package railwaycrossing.model.vehicles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

//import edu.nd.sarec.railwaycrossing.model.infrastructure.Direction;
//import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
import railwaycrossing.model.infrastructure.Direction;
import railwaycrossing.model.infrastructure.gate.CrossingGate;


/**
 * Very basic car factory.  Creates the car and registers it with the crossing gate and the car infront of it.
 * @author jane
 *
 */
public class CarFactory extends Observable implements Observer {

	private Collection<CrossingGate> gates = null;
	private CarFactory otherFac = null;
	private CrossingGate otherGate = null;
	private Car previousCar = null;
	private ArrayList<Car> cars = new ArrayList<Car>();
	private ArrayList<Car> westCars = new ArrayList<Car>();
	private Car westCar = null;
	Direction direction;
	Point location;
	int counter = 0;

	public CarFactory(){}

	public CarFactory(Direction direction, Point location, Collection<CrossingGate> gates, CrossingGate oG, CarFactory oF){
		this.direction = direction;
		this.location = location;
		this.gates = gates;
		this.otherGate = oG;
		this.otherFac = oF;
	}


	// Most code here is to create random speeds
	public Car buildCar(){
		if (previousCar == null || (direction == Direction.SOUTH && location.y < previousCar.getVehicleY()-100)) { //|| (direction == Direction.WEST  && location.y < previousCar.getVehicleX()-100)){
			Car car = new Car(location.x,location.y, this.direction, counter, previousCar);
			counter++;
			double speedVariable = (Math.random() * 10)/10;
			car.setSpeed((2-speedVariable)*1.5);

			// All cars created by this factory must be aware of crossing gates in the road
			for(CrossingGate gate: gates){
				gate.addObserver(car);
				if(gate != null && gate.getTrafficCommand()=="STOP")
					car.setGateDownFlag(false);
			}

			// Each car must observe the car infront of it so it doesn't collide with it.
			if (previousCar != null)
				previousCar.addObserver(car);
			previousCar = car;

			cars.add(car);
			//northCars.add(car);
			return car;
		} else
			return null;
	}

	public void addCar(Car newCar) { // Adds car to the Cars array
		previousCar.addObserver(newCar);
		newCar.setVehicleX((double) 424);
		newCar.setDirection(Direction.SOUTH);
		cars.add(newCar);
		previousCar = newCar;
	}
	public Car getFirstCar() { // Returns the car farthest along the road
		if(cars.size() > 0)
			return cars.get(0);
		else {
			return null;
		}
	}
	public Car getPreviousCar() { // Returns the newest car on the road
		return previousCar;
	}

	public void setPreviousCar(Car newCar) {
		this.previousCar = newCar;
	}
	// We will get a concurrency error if we try to delete cars whilst iterating through the array list
	// so we perform this in two stages.
	// 1.  Loop through the list and identify which cars are off the screen.  Add them to 'toDelete' array.
	// 2.  Iterate through toDelete and remove the cars from the original arrayList.
	public ArrayList<Car> removeOffScreenCars() {
		// Removing cars from the array list.
		ArrayList<Car> toDelete = new ArrayList<Car>();
		Car turningCar = null;
		Direction first = Direction.NORTH;
		Direction second = Direction.NORTH;

		setChanged();
		notifyObservers();
		// Iterate through all cars
		for(int i = 0; i < cars.size(); i++) {

			first = cars.get(i).getDirection();
			cars.get(i).move(otherFac);
			turningCar = cars.get(i);
			second = turningCar.getDirection();
			if(first == Direction.SOUTH && second == Direction.WEST){ // If car changed direction
				if(westCar != null) { // West car is newest car that has turned
					turningCar.setLeadCar(westCar);
				}
				westCar = turningCar;
				for(CrossingGate gate: gates){
					gate.deleteObserver(turningCar);
				}
				otherGate.addObserver(otherGate);
				if(previousCar.getId() == turningCar.getId())
					this.previousCar = null; // Newest car on road cannot be the turning car
				if(westCars.size() == 0)
					westCars.add(turningCar);
				else {
					westCars.get(westCars.size() - 1).addObserver(turningCar);
					westCars.add(turningCar);
				}

			}

			if (cars.get(i).offScreen()) {
				if(cars.get(i).getDirection() == Direction.WEST)
					westCars.remove(0);
				toDelete.add(cars.get(i));
			}

		}

		for (Car car: toDelete)
			cars.remove(car);
		return toDelete;

	}


	public void update(Observable o, Object arg1) {
		if (o instanceof CarFactory){ // Right road/EastWest road get updated version of left road
			CarFactory leftFac = (CarFactory)o;
			this.otherFac = leftFac;
		}
	}
}