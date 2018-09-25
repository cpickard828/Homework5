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
	private Car southCar = null;
	private Car northCar = null;
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
			boolean turnedWest = false;
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

	public void addCar(Car newCar) {
		previousCar.addObserver(newCar);
		newCar.setVehicleX((double) 424);
		newCar.setDirection(Direction.SOUTH);
		cars.add(newCar);
		previousCar = newCar;
	}
	public Car getFirstCar() {
		return cars.get(0);
	}
	public Car getPreviousCar() {
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
		ArrayList<Car> toDeleteWest = new ArrayList<Car>();
		//Car previousCar = null;
		//Car saveCar = null;
		//boolean needReplace = false;
		Car turningCar = null;
		Car leadCar = null;
		Car behindCar = null;
		Car tempLead = null;
		Direction first = Direction.NORTH;
		Direction second = Direction.NORTH;
		//for(Car car: cars){
		setChanged();
		notifyObservers();
		for(int i = 0; i < cars.size(); i++) {

			first = cars.get(i).getDirection();
			tempLead = cars.get(i).getLeadCar();
			cars.get(i).move(otherFac);
			turningCar = cars.get(i);
			second = turningCar.getDirection();
			if(first == Direction.SOUTH && second == Direction.WEST){
				if(westCar != null) {
					turningCar.setLeadCar(westCar);
				}
				westCar = turningCar;
				for(CrossingGate gate: gates){
					gate.deleteObserver(turningCar);
					//if(gate != null && gate.getTrafficCommand()=="STOP")
						//turningCar.setGateDownFlag(false);
				}
				otherGate.addObserver(otherGate);
				if(previousCar.getId() == turningCar.getId())
					this.previousCar = null;
				if(westCars.size() == 0)
					westCars.add(turningCar);
				else {
					westCars.get(westCars.size() - 1).addObserver(turningCar);
					westCars.add(turningCar);
				}

			}
			/*if(first == Direction.SOUTH && second == Direction.WEST) {

				turningCar = cars.get(i);
				if(i-1 > -1)
					leadCar = cars.get(i-1);
				if(i+1 < cars.size())
					behindCar = cars.get(i+1);

				if(leadCar != null)
					leadCar.deleteObserver(turningCar); // deletes turning car as an observer
				if(i+1 < cars.size()) {
					turningCar.deleteObserver(behindCar); // turning car deletes its observer
					if(i-1 > -1) {
						leadCar.addObserver(behindCar); //
						behindCar.setLeadY(leadCar.getVehicleY());

					}
				}

			}
			*/
			if (cars.get(i).offScreen()) {
				if(cars.get(i).getDirection() == Direction.WEST)
					westCars.remove(0);
				toDelete.add(cars.get(i));
			}

		}
		//for(Car Wcar: toDeleteWest)
			//westCars.remove(Wcar);
		//if(previousCar != null & previousCar.getVehicleY() > 260) {
		//	setChanged();
		//	notifyObservers();
		//}
		for (Car car: toDelete)
			cars.remove(car);
		//setChanged();
		//notifyObservers();
		return toDelete;

	}

	public Car extractWestCar() {
		Car newCar = this.westCars.get(0);
		westCars.remove(0);
		return newCar;
	}
	public void update(Observable o, Object arg1) {
		if (o instanceof CarFactory){
			CarFactory leftFac = (CarFactory)o;
			this.otherFac = leftFac;
			//if(this.westCars.size() > 0 && this.westCars.get(0).getVehicleX() < 435)
				//leftFac.addCar(this.extractWestCar());
		}
	}
}