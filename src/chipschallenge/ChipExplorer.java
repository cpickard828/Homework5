package chipschallenge;

// Cameron Pickard

import java.awt.Point;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.layout.AnchorPane;

public class ChipExplorer extends Application {
	AnchorPane root;
	final private int cellSize = 28;
	final private int mazeSize = 23;
	boolean foundTarget = false;
	Scene scene;
	ImageView chipImageView, pirate1ImageView, pirate2ImageView;
	Image chipDImage, chipUImage, chipLImage, chipRImage, pirate1Image, pirate2Image;
	GameMap map = new Level1Map();
	GameMap map2;
    //Pane root;
    Point targetPoint;
    Chip chipPlayer;
    Thread thread;
    int numRestarts = 0;
    int x = 12;
    int y = 12;
    int p1x;
    int p2x;
    int p1y;
    int p2y;
    int mov = 0;
    int currLevel = 1;
    boolean won = false;
    final Canvas canvas = new Canvas(900, 1000);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Random rx = new Random();
    Random ry = new Random();
    ObservableList<Node> myList;
	public void start(Stage stage) throws Exception {
		root = new AnchorPane();

		myList = root.getChildren();
		map.drawMap(myList, cellSize, gc, 0); // all blue squares
		chipPlayer = new Chip(x, y, cellSize, map);

		loadImages();


		scene = new Scene(root,mazeSize*cellSize + cellSize*2,mazeSize*cellSize + cellSize*2);
		stage.setTitle("Chip's Challenge");
		stage.setScene(scene);
		stage.setWidth((double) 900);
		stage.show();

		root.getChildren().add(canvas);
		startPlaying(stage);
	}

	private void loadImages(){


		// Load Pirate 1's chip


		// Load chip textures
		chipDImage = new Image("images\\chipDown.png",cellSize, cellSize, false, true);
		chipUImage = new Image("images\\chipUp.png",cellSize, cellSize, false, true);
		chipRImage = new Image("images\\chipRight.png",cellSize, cellSize, false, true);
		chipLImage = new Image("images\\chipLeft.png",cellSize, cellSize, false, true);
		chipImageView = new ImageView(chipDImage);
		chipImageView.setX(chipPlayer.getLocation().x*cellSize);
		chipImageView.setY(chipPlayer.getLocation().y*cellSize);


		root.getChildren().add(chipImageView);

    }

	private void startPlaying(Stage stage){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
		public void handle(KeyEvent ke) {

			switch(ke.getCode()){
				case RIGHT:
					if(won)
						break;
					mov = map.move(chipPlayer.getLocation().x + 1, chipPlayer.getLocation().y, gc);
					if(chipPlayer.goEast() && mov>0) {
						chipImageView.setImage(chipRImage);
						chipPlayer.setPos(chipPlayer.getLocation().x + 1, chipPlayer.getLocation().y);
						//chipImage = new Image("images\\chipRight.png",cellSize, cellSize, false, true);
						//chipImageView = new ImageView(chipImage);
						//root.getChildren().add(chipImageView);
						//chipImageView.setX(chipPlayer.getLocation().x*cellSize);
						//chipImageView.setY(chipPlayer.getLocation().y*cellSize);
					}
					break;
				case LEFT:
					if(won)
						break;
					mov = map.move(chipPlayer.getLocation().x - 1, chipPlayer.getLocation().y, gc);
					if(chipPlayer.goWest() && mov>0) {
						chipImageView.setImage(chipLImage);
						chipPlayer.setPos(chipPlayer.getLocation().x - 1, chipPlayer.getLocation().y);
					}
					break;
				case UP:
					if(won)
						break;
					mov = map.move(chipPlayer.getLocation().x, chipPlayer.getLocation().y - 1, gc);
					if(chipPlayer.goNorth() && mov>0) {
						chipImageView.setImage(chipUImage);
						chipPlayer.setPos(chipPlayer.getLocation().x, chipPlayer.getLocation().y - 1);
					}

					break;
				case DOWN:
					if(won)
						break;
					mov = map.move(chipPlayer.getLocation().x, chipPlayer.getLocation().y + 1, gc);
					if(chipPlayer.goSouth() && mov>0) {
						chipImageView.setImage(chipDImage);
						chipPlayer.setPos(chipPlayer.getLocation().x, chipPlayer.getLocation().y + 1);
					}
					break;
				case SPACE: // reset game
					if(won)
						break;
					setStage(currLevel, 1);
					numRestarts += 1;
					break;
				case ESCAPE: // quit game
					stage.close();
					break;
				case Q: // quit game
					stage.close();
					break;
				default:
					break;


			}
			if(mov==2) { // reached finish portal
				map2 = map;
				if(map2.getNextLevel() == null) { // if there is no other level
					map.drawWinScreen(gc);
					won = true;
				}
				else { // if there is another level
					map = map2.getNextLevel();
					currLevel = 2;
					setStage(3, 0);
				}
			}
			// Update images
			chipImageView.setX(chipPlayer.getLocation().x*cellSize);
			chipImageView.setY(chipPlayer.getLocation().y*cellSize);


		}
	});
	}

	// reset / set level
	private void setStage(int i, int newRes) {
		if(i == 1)
			map = new Level1Map();
		if(i==2)
			map = new Level2Map();
		myList = root.getChildren();
		map.drawMap(myList, cellSize, gc, numRestarts + newRes);
		chipPlayer = new Chip(x, y, cellSize, map);


		loadImages();
	}
	public static void main(String[] args) {
     	launch(args);
    }
}