package chipschallenge;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;


public interface UIColorHandler {
	//public void draw(GameMap map, GraphicsContext gc);
	public int draw(int currY, int shortSpacing, int spacing, int keys, GraphicsContext gc);
	public void writeText(GraphicsContext gc, int fontSize, boolean bold, String text, int x, int y, Paint p);
}