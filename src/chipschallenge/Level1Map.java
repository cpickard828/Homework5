package chipschallenge;

// Cameron Pickard
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;




public class Level1Map implements GameMap {
	// 0 = Ground Tile
	// 1 = Wall
	// 2 = Chip item
	// 3 = Chip gate
	// 4 = Finish portal
	// 5 = red key door
	// 6 = red key
	// 7 = blue key door
	// 8 = blue key
	private GameMap nextLevel = new Level2Map();
	private boolean nextL= true;

	int[][] mapGrid = new int[][]{
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 2, 0, 0, 5, 0, 0, 0, 1, 0, 2, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 8, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 6, 0, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 0, 0, 0, 0, 4, 0, 0, 0, 1},
		{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1},
		{1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 7, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 5, 1, 1},
		{1, 5, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 7, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 8, 1, 5, 1, 0, 0, 2, 0, 0, 1, 0, 6, 0, 0, 0, 0, 7, 0, 0, 0, 6, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 8, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1},
		{1, 7, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	//Image[][] levelImage = new Image[25][25];
	Image tileImage, wallImage, chipItemImage, chipGateImage, finishImage, bKeyImage, rKeyImage, yKeyImage, bDoorImage, rDoorImage, yDoorImage;

	ImageView[][] levelImageView = new ImageView[25][25];

	UIRedHandler redHandler = new UIRedHandler();
	UIBlueHandler blueHandler = new UIBlueHandler();
	UIYellowHandler yellowHandler = new UIYellowHandler();
	UIGreenHandler greenHandler = new UIGreenHandler();

	public int blueKeys, redKeys, greenKeys, yellowKeys;
	int chipsLeft;
	boolean blue, red, green, yellow = false;
	final int dimensions = 25;
	int restarts;
	public int currY, spacing, shortSpacing;

	// Create the map
	public void drawMap(ObservableList<Node> root, int scale, GraphicsContext gc, int res) {
		restarts = res;
		chipsLeft = 6;
		blueKeys = 0;
		blue = true;
		redKeys = 0;
		red = true;
		greenKeys = 0;
		green = false;
		yellowKeys = 0;
		yellow = false;

		// Load references to all textures
		tileImage = new Image("images\\BlankTile.png",scale, scale, false, true);
		wallImage  = new Image("images\\wall.png",scale, scale, false, true);
		chipItemImage = new Image("images\\chipItem.png",scale, scale, false, true);
		chipGateImage = new Image("images\\chipGate.png",scale, scale, false, true);
		finishImage = new Image("images\\finish.png",scale, scale, false, true);
		bKeyImage = new Image("images\\blueKey.png",scale, scale, false, true);
		rKeyImage = new Image("images\\redKey.png",scale, scale, false, true);
		yKeyImage = new Image("images\\yellowKey.png",scale, scale, false, true);
		bDoorImage = new Image("images\\blueKeyWall.png",scale, scale, false, true);
		rDoorImage = new Image("images\\redKeyWall.png",scale, scale, false, true);
		yDoorImage = new Image("images\\yellowKeyWall.png",scale, scale, false, true);
		this.drawScore(gc);
		for(int x = 0; x < dimensions; x++){
			for(int y = 0; y < dimensions; y++) {
				switch(mapGrid[x][y]) {
					case 0:
						levelImageView[x][y] = new ImageView(tileImage);
						break;
					case 1:
						levelImageView[x][y] = new ImageView(wallImage);
						break;
					case 2:
						levelImageView[x][y] = new ImageView(chipItemImage);
						break;
					case 3:
						levelImageView[x][y] = new ImageView(chipGateImage);
						break;
					case 4:
						levelImageView[x][y] = new ImageView(finishImage);
						break;
					case 5:
						levelImageView[x][y] = new ImageView(rDoorImage);
						break;
					case 6:
						levelImageView[x][y] = new ImageView(rKeyImage);
						break;
					case 7:
						levelImageView[x][y] = new ImageView(bDoorImage);
						break;
					case 8:
						levelImageView[x][y] = new ImageView(bKeyImage);
						break;
				}
				levelImageView[x][y].setX(x*scale);
				levelImageView[x][y].setY(y*scale);

				root.add(levelImageView[x][y]);

			}
		}
	}

	// Return status of space
	public int seeSpace(int x, int y) {
		return mapGrid[x][y];
	}

	public int move(int x, int y, GraphicsContext gc) {
		// check what space you're moving onto
		// return 0 if cant move
		// return 1 if can move
		// return 2 if beat level
		switch(mapGrid[x][y]) {
			case 0: // open tile
				return 1;
			case 1: // wall
				return 0;
			case 2: // chip item
				chipsLeft -= 1;
				mapGrid[x][y] = 0;
				levelImageView[x][y].setImage(tileImage);
				this.drawScore(gc);
				return 1;
			case 3: // chip gate
				if(chipsLeft == 0) {
					mapGrid[x][y] = 0;
					levelImageView[x][y].setImage(tileImage);
					return 1;
				}
				break;
			case 4: // finish portal
				return 2;
			case 5: // red door
				if(redKeys > 0) {
					redKeys--;
					mapGrid[x][y] = 0;
					levelImageView[x][y].setImage(tileImage);
					this.drawScore(gc);
					return 1;
				}
				return 0;
			case 6: // red key
				redKeys++;
				mapGrid[x][y] = 0;
				levelImageView[x][y].setImage(tileImage);
				this.drawScore(gc);
				return 1;
			case 7: // blue door
				if(blueKeys > 0) {
					blueKeys--;
					mapGrid[x][y] = 0;
					levelImageView[x][y].setImage(tileImage);
					this.drawScore(gc);
					return 1;
				}
				return 0;
			case 8: // blue key
				blueKeys++;
				mapGrid[x][y] = 0;
				levelImageView[x][y].setImage(tileImage);
				this.drawScore(gc);
				return 1;
		}
		return 0;
	}
	public void updateTile(ObservableList<Node> root, int x, int y) {

	}

	// writes text in graphics context
	public void writeText(GraphicsContext gc, int fontSize, boolean bold, String text, int x, int y, Paint p){
		gc.setFill(p);
		if (bold)
			gc.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
		else
			gc.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
		gc.fillText(text, x, y);
		gc.setStroke(Color.BLACK);
	}

	// update scoreboard
	public void drawScore(GraphicsContext gc) {
		gc.clearRect(700, 0, 200, 700);
		gc.setFill(Color.BLACK);
		spacing = 60;
		shortSpacing = 30;
		//gc.fillRect(200, 200, 500, 500);
		gc.fillRect(700, 0, 200, 700);
		currY = 35;
		this.writeText(gc, 25, true, "LEVEL 1", 740, currY, Color.WHITE);
		currY+=spacing + 50;
		this.writeText(gc, 20, true, "Chips Remaining", 710, currY, Color.WHITE);
		currY+=shortSpacing;
		this.writeText(gc, 30, true, Integer.toString(chipsLeft), 785, currY, Color.LEMONCHIFFON);
		currY+=spacing;
		if(red) {
			this.addCurrY(redHandler.draw(currY, shortSpacing, shortSpacing, redKeys, gc));
		}

		if(blue) {
			this.addCurrY(blueHandler.draw(currY, shortSpacing, shortSpacing, blueKeys, gc));
		}

		if(yellow) {
			this.addCurrY(yellowHandler.draw(currY, shortSpacing, shortSpacing, yellowKeys, gc));
		}
		if(green) {
			this.addCurrY(greenHandler.draw(currY, shortSpacing, shortSpacing, greenKeys, gc));
		}
		this.writeText(gc, 14, true, "Spacebar - Reset Level", 715, 660, Color.WHITE);
		this.writeText(gc, 14, false, "Resets: " + Integer.toString(restarts), 760, 680, Color.WHITE);
	}
	public void drawWinScreen(GraphicsContext gc) {}

	// called when level is beat
	public GameMap getNextLevel() {
		if(nextL)
			return nextLevel;
		return null;
	}

	public void addCurrY(int i)  {
		currY+=i;
	}
}
