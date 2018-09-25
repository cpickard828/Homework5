//package edu.nd.sarec.railwaycrossing.model.infrastructure.gate;
package railwaycrossing.model.infrastructure.gate;
import java.util.Observable;
import java.util.Observer;

import railwaycrossing.model.infrastructure.Direction;
//import edu.nd.sarec.railwaycrossing.model.vehicles.Train;
import railwaycrossing.model.vehicles.Train;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Context class for Crossing Gate
 * @author jane
 *
 */
public class CrossingGate extends Observable implements Observer{

	// Crossing Gate location and its trigger & exit points
	private int anchorX;
	private int anchorY;
	private int movingX;
	private int movingY;
	private int triggerPoint;
	private int exitPoint;

	private IGateState gateClosed;
	private IGateState gateOpen;
	private IGateState gateClosing;
	private IGateState gateOpening;
	private IGateState currentGateState;
	private Line line;

	String gateName;

	public CrossingGate(){}

	public CrossingGate(int xPosition, int yPosition, String crossingGate){
		anchorX = xPosition;
		anchorY = yPosition;
		movingX = anchorX;
		movingY = anchorY-60;
		triggerPoint = anchorX+250;
		exitPoint = anchorX-250;

		// Gate elements
		line = new Line(anchorX, anchorY,movingX,movingY);
		line.setStroke(Color.RED);
	    line.setStrokeWidth(10);

		// Gate States
		gateClosed = new GateClosed(this);
		gateOpen = new GateOpen(this);
		gateOpening = new GateOpening(this);
		gateClosing = new GateClosing(this);
		currentGateState = gateOpen;
		gateName = crossingGate;
	}

	public Line getGateLine(){
		return line;
	}

	public void operateGate(){
		currentGateState.operate();
	}

	public void close(){
		if (movingY<anchorY){
		    movingX+=1;
		    movingY+=1;
			line.setStartX(anchorX);
			line.setStartY(anchorY);
			line.setEndX(movingX);
			line.setEndY(movingY);
		} else {
			currentGateState.gateFinishedClosing();
		}
	}

	public void open(){
		if (movingX>anchorX){
			movingX-=1;
			movingY-=1;
			line.setStartX(anchorX);
			line.setStartY(anchorY);
			line.setEndX(movingX);
			line.setEndY(movingY);
		}  else {
			currentGateState.gateFinishedOpening();
		}
	}

	// State getters and setters
	public IGateState getGateClosedState(){
		return gateClosed;
	}
	public IGateState getGateOpenState(){
		return gateOpen;
	}
	public IGateState getGateClosingState(){
		return gateClosing;
	}
	public IGateState getGateOpeningState(){
		return gateOpening;
	}

	public void setGateState(IGateState newState){
		currentGateState = newState;
		setChanged();
		notifyObservers();
	}

	public String getTrafficCommand(){
		return currentGateState.getTrafficAction();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Train){ // If notified by  a train
			Train train = (Train)o;

			if ((train.getDirection() == Direction.WEST && train.getVehicleX() < exitPoint + 20 && train.getVehicleX() > exitPoint)) { //(train.getDirection() == Direction.EAST && train.getVehicleX() > triggerPoint))
				currentGateState.leaveStation();

			}
			else if((train.getDirection() == Direction.WEST && train.getVehicleX() < triggerPoint + 40 && train.getVehicleX() > exitPoint + 20)) { // (train.getDirection() == Direction.EAST && train.getVehicleX() > exitPoint - 50)){
				currentGateState.approachStation();
				setChanged();
				notifyObservers();

			}

			// Trigger points and exit points swapped for EAST train
			if ((train.getDirection() == Direction.EAST && train.getVehicleX() > triggerPoint && train.getVehicleX() < triggerPoint + 10)) { //(train.getDirection() == Direction.EAST && train.getVehicleX() > triggerPoint))
				currentGateState.leaveStation();

			}
			else if((train.getDirection() == Direction.EAST && train.getVehicleX() > exitPoint - 50 && train.getVehicleX() < triggerPoint)) { // (train.getDirection() == Direction.EAST && train.getVehicleX() > exitPoint - 50)){
				currentGateState.approachStation();
				setChanged();
				notifyObservers();

			}
		}
	}
}