package chipschallenge;

// Cameron Pickard
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;



public interface GameMap {
	//int[][] oceanGrid = new int[25][25]; // 0 empty, 1 island
	//final int dimensions = 25;

	public int currY = 0;
	public int spacing = 0;
	public int shortSpacing = 0;

	public int blueKeys = 0, redKeys = 0, greenKeys = 0, yellowKeys = 0;

	// Create the sea
	public void drawMap(ObservableList<Node> root, int scale, GraphicsContext gc, int res);
	// Return status of space
	public int seeSpace(int x, int y);
	public int move(int x, int y, GraphicsContext gc);
	public void updateTile(ObservableList<Node> root, int x, int y);
	public void writeText(GraphicsContext gc, int fontSize, boolean bold, String text, int x, int y, Paint p);
	public void drawScore(GraphicsContext gc);
	public void drawWinScreen(GraphicsContext gc);
	public GameMap getNextLevel();
	public void addCurrY(int i);
}
