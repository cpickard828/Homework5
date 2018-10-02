package chipschallenge;

// Cameron Pickard

import java.awt.Point;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.layout.AnchorPane;

public class OceanExplorer extends Application {
	AnchorPane root;
	final private int cellSize = 28;
	final private int mazeSize = 23;
	boolean foundTarget = false;
	Scene scene;
	ImageView shipImageView, pirate1ImageView, pirate2ImageView;
	Image shipImage, pirate1Image, pirate2Image;
	Level1Map map = new Level1Map();
    //Pane root;
    Point targetPoint;
    Ship columbusShip;
    Thread thread;
    int x = 10;
    int y = 10;
    int p1x;
    int p2x;
    int p1y;
    int p2y;
    Random rx = new Random();
    Random ry = new Random();
    PirateShip pirate1, pirate2;
    //scene = new Scene(root,mazeSize*cellSize + cellSize*2,mazeSize*cellSize + cellSize*2);
	public void start(Stage stage) throws Exception {
		root = new AnchorPane();

		ObservableList<Node> myList = root.getChildren();
		map.drawMap(myList, cellSize); // all blue squares
		//columbusShip = new Ship(x, y, cellSize, map);
		//map.drawIslands(myList, cellSize, x, y); // add 10 green squares
		columbusShip = new Ship(x, y, cellSize, map);

		int madeIsland = 0;
		// randomly place pirate 1

		loadImages();
		scene = new Scene(root,mazeSize*cellSize + cellSize*2,mazeSize*cellSize + cellSize*2);
		stage.setTitle("Columbus Game");
		stage.setScene(scene);

		stage.show();
		startSailing(stage);
	}

	private void loadImages(){


		// Load Pirate 1's ship


		// Load Columbus' ship
		shipImage = new Image("images\\chipDown.png",cellSize, cellSize, false, true);
		shipImageView = new ImageView(shipImage);
		shipImageView.setX(columbusShip.getLocation().x*cellSize);
		shipImageView.setY(columbusShip.getLocation().y*cellSize);


		root.getChildren().add(shipImageView);

    }

	private void startSailing(Stage stage){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent ke) {
			switch(ke.getCode()){
				case RIGHT:
					columbusShip.goEast();
					break;
				case LEFT:
					columbusShip.goWest();
					break;
				case UP:
					columbusShip.goNorth();
					break;
				case DOWN:
					columbusShip.goSouth();
					break;
				default:
					break;
			}
			// Update images
			shipImageView.setX(columbusShip.getLocation().x*cellSize);
			shipImageView.setY(columbusShip.getLocation().y*cellSize);


		}
	});
	}
	public static void main(String[] args) {
     	launch(args);
    }
}
