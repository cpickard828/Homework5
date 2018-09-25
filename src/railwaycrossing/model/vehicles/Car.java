//package edu.nd.sarec.railwaycrossing.model.vehicles;
package railwaycrossing.model.vehicles;

import java.util.Observable;
import java.util.Observer;

import railwaycrossing.model.infrastructure.Direction;
//import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
//import edu.nd.sarec.railwaycrossing.view.CarImageSelector;
import railwaycrossing.model.infrastructure.gate.CrossingGate;
import railwaycrossing.view.CarImageSelector;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Represents Car object
 * @author jane
 *
 */
public class Car extends Observable implements IVehicle, Observer{
	private ImageView ivCar;
	private TextField txtCar;
	private double currentX = 0;
	private double currentY = 0;
	private double originalY = 0;
	private boolean gateDown = false;
	private Car leadCar = null;
	private double leadCarY = -1;  // Current Y position of car directly infront of this one
	private double speed = 0.5;
	private Direction direction;
	private boolean turnLeft = false;
	private int id;
	private boolean keepWest = false;
	/**
	 * Constructor
	 * @param x initial x coordinate of car
	 * @param y initial y coordinate of car
	 */
	public Car(int x, int y, Direction d, int i, Car lC){
		this.currentX = x;
		this.currentY = y;
		this.direction = d;
		this.id = i;
		this.leadCar = lC;
		this.gateDown = false;
		originalY = y;
		ivCar = new ImageView(CarImageSelector.getImage());
		ivCar.setX(getVehicleX());
		ivCar.setY(getVehicleY());
		/*txtCar = new TextField("F");
		txtCar.setText(Integer.toString(i));
		txtCar.setLayoutX(getVehicleX() + 25);
		txtCar.setLayoutY(getVehicleY());
		*/
	}

	@Override
	public Node getImageView() {
		return ivCar;
	}

	public Node getLicenseView() {
		return txtCar;
	}

	public boolean gateIsClosed(){
		return gateDown;
	}

	public double getVehicleX(){
		return currentX;
	}
	public double getVehicleY(){
		return currentY;
	}

	public void setVehicleX(double x) {
		currentX = x;
	}

	public void setDirection(Direction d) {
		this.direction = d;
	}

	public int getId() {
		return this.id;
	}
	public void setText(String string) {
		txtCar.setText(string);
		txtCar.setLayoutX(getVehicleX() + 25);
		txtCar.setLayoutY(getVehicleY());
	}
	public void move(CarFactory otherFac){
		boolean canMove = true;

		// First case.  Car is at the front of the stopping line.
		if (gateDown && getVehicleY() < 250 && getVehicleY()> 210)
			canMove = false;

		// Second case. Car is too close too other car.
		if (direction == Direction.SOUTH && leadCar!=null && leadCar.getDirection() == Direction.SOUTH && (getDistanceToLeadCar() < 50 && leadCar.getVehicleY() < 1010))
			canMove = false;

		if (direction == Direction.WEST && this.leadCar != null && leadCar.getDirection() == Direction.WEST && (getDistanceToLeadCarX() < 50)) {
			canMove = false;
			//keepWest = false;
		}
		if (direction == Direction.WEST && canMove == true && this.currentX < 420 && this.currentX > 410) {
			canMove = false;
			if(otherFac.getFirstCar() == null || otherFac.getFirstCar().getVehicleY() < 350)
				canMove = true;
		}
		if (canMove){
			if(this.direction == Direction.SOUTH) {
				currentY+=speed;
				ivCar.setY(currentY);
				//txtCar.setLayoutY(currentY);
				if(currentX > 500 && direction == Direction.SOUTH && currentY > 590 && currentY < 610){
					if (leadCarY != -1 && (int)(Math.random() * 100) < 50){
						this.direction = Direction.WEST;
						//this.leadCar = null;
						currentY = 600;
						ivCar.setY(currentY);
						//txtCar.setLayoutY(currentY);
						setChanged();
						notifyObservers(1);
						this.leadCar = null;
					}
				}
			}
			else if(this.direction == Direction.WEST){
				currentX-=speed;
				ivCar.setX(currentX);
				//txtCar.setLayoutX(currentX + 25);
				if(currentX < 391)
					this.direction = Direction.SOUTH;
			}

		}
		setChanged();
		notifyObservers(0);
	}
	public Direction getDirection() {
		return direction;
	}
	public void setSpeed(double speed){
		this.speed = speed;
	}

	public void setGateDownFlag(boolean gateDown){
		this.gateDown = gateDown;
	}

	public boolean offScreen(){
		if (currentY > 1020) //|| this.direction == Direction.WEST && currentX < 400)
			return true;
		else
			return false;
	}

	public void reset(){
		currentY = originalY;
	}

	public double getDistanceToLeadCar(){
		return Math.abs(leadCarY-getVehicleY());
	}

	public double getDistanceToLeadCarX() {
		return Math.abs(leadCar.getVehicleX()-getVehicleX());
	}

	public Car getLeadCar() {
		return this.leadCar;
	}

	public void setLeadCar(Car c) {
		this.leadCar = c;
	}
	public void removeLeadCar(){
		leadCarY = -1;
	}

	public void setLeadY(double y) {
		this.leadCarY = y;
	}

	@Override
	public void update(Observable o, Object arg1) {
		if (o instanceof Car){
			if((int) arg1 == 1) {
				Car newFollow = ((Car)o).getLeadCar();
				((Car)o).deleteObserver(this);
				if(newFollow != null) {
					newFollow.deleteObserver((Car)o);
					newFollow.addObserver(this);
				}
				this.leadCar = newFollow;
			}
			//Direction temp = this.leadCar.getDirection();
			//this.leadCar = (Car)o;
			//if(temp != this.leadCar.getDirection()) {
			//	this.leadCar.deleteObserver(this);
			//	this.leadCar.getLeadCar().deleteObserver(this.leadCar);
			//	this.leadCar.getLeadCar().addObserver(this);

			//}
			else {
				String string = Integer.toString(id) + " / " + Integer.toString(((Car)o).getId());
				//this.setText(string);
				leadCar = ((Car)o);
				leadCarY = (((Car)o).getVehicleY());
				if (leadCarY > 1020 )
					leadCarY = -1;
				if(((Car)o).getDirection() == Direction.WEST) {
					if(this.getDirection() == Direction.SOUTH) {
						((Car)o).deleteObserver(this);
						leadCarY = -1;
					}
				}
			}
		}

		if (o instanceof CrossingGate){
			CrossingGate gate = (CrossingGate)o;
			if(gate.getTrafficCommand()=="STOP")
				gateDown = true;
			else
				gateDown = false;

		}
	}
}