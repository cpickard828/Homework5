//package edu.nd.sarec.railwaycrossing.model.vehicles;
package railwaycrossing.model.vehicles;

import java.util.Observable;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import railwaycrossing.model.infrastructure.Direction;

/**
 * Represents the train entity object
 * @author jane
 *
 */
public class Train extends Observable implements IVehicle{
	private double currentX = 0;
	private double currentY = 0;
	private double originalX = 0;
	private Image img;
	private ImageView imgView;
	private int trainLength = 35;
	private Direction direction;

	public Train(int x, int y, Direction d){
		this.currentX = x;
		this.currentY = y;
		this.direction = d;
		originalX = x;
		if(d == Direction.WEST)
			img = new Image("images\\Train.PNG",120,trainLength,false,false);
		if(d == Direction.EAST)
			img = new Image("images\\TrainReverse.PNG",120,trainLength,false,false);
		imgView = new ImageView(img);
		imgView.setX(currentX);
		imgView.setY(currentY);
	}

	public double getVehicleX(){
		return currentX;
	}

	public double getVehicleY(){
		return currentY;
	}

	public Direction getDirection() {
		return direction;
	}
	public void move(CarFactory cF){
		if(this.direction == Direction.WEST) {
			currentX-=2;
		}
		else {
			currentX+=2;
		}
		imgView.setX(currentX);
		setChanged();
		notifyObservers();
	}

	public boolean offScreen(){
		if (currentX < -200 || currentX > 1400)
			return true;
		else
			return false;
	}

	public void reset(){
		currentX = originalX;
	}

	//@Override
	public Node getImageView() {
		return imgView;
	}
}
