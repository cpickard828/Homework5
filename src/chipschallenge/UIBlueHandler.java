package chipschallenge;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIBlueHandler implements UIColorHandler {
	public int draw(int currY, int shortSpacing, int spacing, int keys, GraphicsContext gc) {
		int ourY = currY;
		this.writeText(gc, 20, true, "Blue Keys", 710, ourY, Color.LIGHTSKYBLUE);
		ourY += shortSpacing;
		this.writeText(gc, 30, true, Integer.toString(keys), 785, ourY, Color.LEMONCHIFFON);
		ourY += spacing;
		return(shortSpacing + spacing);
	}

	public void writeText(GraphicsContext gc, int fontSize, boolean bold, String text, int x, int y, Paint p){
		gc.setFill(p);
		if (bold)
			gc.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
		else
			gc.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
		gc.fillText(text, x, y);
		gc.setStroke(Color.BLACK);
	}
}