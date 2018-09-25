//package edu.nd.sarec.railwaycrossing;
package railwaycrossing;

import java.util.ArrayList;
import java.util.Collection;

import railwaycrossing.model.infrastructure.Direction;
//import edu.nd.sarec.railwaycrossing.model.infrastructure.MapBuilder;
//import edu.nd.sarec.railwaycrossing.model.infrastructure.RailwayTracks;
//import edu.nd.sarec.railwaycrossing.model.infrastructure.Road;
//import edu.nd.sarec.railwaycrossing.model.infrastructure.gate.CrossingGate;
//import edu.nd.sarec.railwaycrossing.model.vehicles.Car;
//import edu.nd.sarec.railwaycrossing.model.vehicles.Train;
//import edu.nd.sarec.railwaycrossing.view.MapDisplay;
import railwaycrossing.model.infrastructure.MapBuilder;
import railwaycrossing.model.infrastructure.RailwayTracks;
import railwaycrossing.model.infrastructure.Road;
import railwaycrossing.model.infrastructure.gate.CrossingGate;
import railwaycrossing.model.vehicles.Car;
import railwaycrossing.model.vehicles.Train;
import railwaycrossing.view.MapDisplay;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Simulation extends Application{

	private Pane root;
	private Scene scene;
	private MapBuilder mapBuilder;
	private MapDisplay mapDisplay;

	@Override
	public void start(Stage stage) throws Exception {

		root = new Pane();

		// Build infrastructure
		mapBuilder = new MapBuilder();
		mapDisplay = new MapDisplay(root, mapBuilder.getRoads(), mapBuilder.getTracks(),mapBuilder.getAllGates());
		mapDisplay.drawTracks();
		mapDisplay.drawRoad();
		mapDisplay.drawGate();

		scene = new Scene(root,1200,1000);
		stage.setTitle("Railways");
		stage.setScene(scene);
		stage.show();
		//Direction direction;

		// Train
		RailwayTracks track = mapBuilder.getTrack("Royal");
		RailwayTracks track2 = mapBuilder.getTrack("LtoR");
		Train train2 = new Train(-200,track2.getEndY()-25, Direction.EAST);
		root.getChildren().add(train2.getImageView());
	 	Train train = new Train(track.getEndX()+100,track.getEndY()-25, Direction.WEST);
		root.getChildren().add(train.getImageView());
		//Train train2 = new Train(-200,track2.getEndY()-25, Direction.EAST);
		//root.getChildren().add(train2.getImageView());

		for(CrossingGate gate: mapBuilder.getAllGates()) {
			//train.addObserver(gate);
			train2.addObserver(gate);
			train.addObserver(gate);
		}

		// Sets up a repetitive loop i.e., in handle that runs the actual simulation
		new AnimationTimer(){

			@Override
			public void handle(long now) {

				createCar();
				train.move(null);
				train2.move(null);
				for(CrossingGate gate: mapBuilder.getAllGates())
					gate.operateGate();

				if (train.offScreen())
					train.reset();
				if (train2.offScreen())
					train2.reset();

				clearCars();
			}
		}.start();
	}

	// Clears cars as they leave the simulation
	private void clearCars(){
		Collection<Road> roads = mapBuilder.getRoads();
		for(Road road: roads){
			if (road.getCarFactory()!= null){
				ArrayList<Car> junkCars = road.getCarFactory().removeOffScreenCars();
				mapDisplay.removeCarImages(junkCars);
			}
		}
	}

	private void createCar(){
		Collection<Road> roads = mapBuilder.getRoads();
		for(Road road: roads){
			if (road.getCarFactory() != null){
				if ((int)(Math.random() * 100) == 15){
					Car car = road.getCarFactory().buildCar();
					if (car != null){
						root.getChildren().add(car.getImageView());
						//root.getChildren().add(car.getLicenseView());
					}
				}
			}
		}
	}

	public static void main(String[] args){
		launch(args);
	}
}