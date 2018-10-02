package chipschallenge;

// Cameron Pickard
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;



public interface GameMap {
	//int[][] oceanGrid = new int[25][25]; // 0 empty, 1 island
	//final int dimensions = 25;

	// Create the sea
	public void drawMap(ObservableList<Node> root, int scale);
	// Add 10 islands randomly
	public void drawIslands(ObservableList<Node> root, int scale, int x, int y);

	// Return status of space
	public int seeSpace(int x, int y);

	public void updateTile(ObservableList<Node> root, int x, int y);
}
