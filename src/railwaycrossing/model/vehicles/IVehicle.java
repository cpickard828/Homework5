//package edu.nd.sarec.railwaycrossing.model.vehicles;
package railwaycrossing.model.vehicles;

import javafx.scene.Node;
import railwaycrossing.model.infrastructure.Direction;


public interface IVehicle {
	public Node getImageView();
	public double getVehicleX();
	public double getVehicleY();
	public Direction getDirection();
	public void move(CarFactory cF);
	public boolean offScreen();
	public void reset();
}