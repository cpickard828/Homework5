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




public class Level2Map implements GameMap {
	// 0 = Ground Tile
	// 1 = Wall
	// 2 = Chip item
	// 3 = Chip gate
	// 4 = Finish portal
	// 5 = red key door
	// 6 = red key
	// 7 = blue key door
	// 8 = blue key
	// 9 = yellow key door
	// 10 = yellow key
	private Level2Map nextLevel = null;
	private boolean nextL= false;

	int[][] mapGrid = new int[][]{
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 9, 0, 0, 0, 1, 1, 7, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 8, 1, 0, 0, 0, 5, 0, 2, 0, 7, 0, 10, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 5, 1, 1, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 1, 1, 3, 1, 1, 0, 1, 1, 7, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1},
		{1, 1, 1, 1, 0, 4, 0, 1, 1, 1, 8, 0, 0, 7, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1},
		{1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 1, 1, 3, 1, 1, 0, 1, 1, 7, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 7, 1, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 1},
		{1, 0, 0, 0, 0, 0, 10, 0, 0, 0, 1, 0, 1, 1, 9, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 10, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 6, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	Image tileImage, wallImage, chipItemImage, chipGateImage, finishImage, bKeyImage, rKeyImage, yKeyImage, bDoorImage, rDoorImage, yDoorImage;

	ImageView[][] levelImageView = new ImageView[25][25];

	UIRedHandler redHandler = new UIRedHandler();
	UIBlueHandler blueHandler = new UIBlueHandler();
	UIYellowHandler yellowHandler = new UIYellowHandler();
	UIGreenHandler greenHandler = new UIGreenHandler();

	public int blueKeys, redKeys, greenKeys, yellowKeys;
	public int currY, spacing, shortSpacing;
	int chipsLeft;
	boolean blue, red, green, yellow = false;
	final int dimensions = 25;
	int restarts;
	boolean won = false;
	// Create the map
	public void drawMap(ObservableList<Node> root, int scale, GraphicsContext gc, int res) {

		restarts = res;
		chipsLeft = 2;
		blueKeys = 0;
		blue = true;
		redKeys = 0;
		red = true;
		greenKeys = 0;
		green = false;
		yellowKeys = 0;
		yellow = true;

		// Load all textures
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
					case 0: // open tile
						levelImageView[x][y] = new ImageView(tileImage);
						break;
					case 1: // wall
						levelImageView[x][y] = new ImageView(wallImage);
						break;
					case 2: // chip item
						levelImageView[x][y] = new ImageView(chipItemImage);
						break;
					case 3: // chip gate
						levelImageView[x][y] = new ImageView(chipGateImage);
						break;
					case 4: // finish portal
						levelImageView[x][y] = new ImageView(finishImage);
						break;
					case 5: // red door
						levelImageView[x][y] = new ImageView(rDoorImage);
						break;
					case 6: // red key
						levelImageView[x][y] = new ImageView(rKeyImage);
						break;
					case 7: // blue door
						levelImageView[x][y] = new ImageView(bDoorImage);
						break;
					case 8: // blue key
						levelImageView[x][y] = new ImageView(bKeyImage);
						break;
					case 9: // yellow door
						levelImageView[x][y] = new ImageView(yDoorImage);
						break;
					case 10: // yellow key
						levelImageView[x][y] = new ImageView(yKeyImage);
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

	// move to space
	public int move(int x, int y, GraphicsContext gc) {

		switch(mapGrid[x][y]) { // check next space
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
			case 9: // yellow door
				if(yellowKeys > 0) {
					yellowKeys--;
					mapGrid[x][y] = 0;
					levelImageView[x][y].setImage(tileImage);
					this.drawScore(gc);
					return 1;
				}
				return 0;
			case 10: // yellow key
				yellowKeys++;
				mapGrid[x][y] = 0;
				levelImageView[x][y].setImage(tileImage);
				this.drawScore(gc);
				return 1;
		}
		return 0;
	}
	public void updateTile(ObservableList<Node> root, int x, int y) {

	}

	// write text
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
		this.writeText(gc, 25, true, "LEVEL 2", 740, currY, Color.WHITE);
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
		//gc.setFill(Color.RED);
		//this.writeText(100, true, "test", 700, 150);
		//this.writeText(100, true, "test", 700, 330);
		//this.writeText(100, true, "fool", 700, 330);
		//gc.clearRect(700, 200, 100, 100);
	}

	// draw the win screen
	public void drawWinScreen(GraphicsContext gc) {
		gc.clearRect(700, 0, 200, 700);
		gc.setFill(Color.BLACK);
		//gc.fillRect(200, 200, 500, 500);
		gc.fillRect(700, 0, 200, 700);
		this.writeText(gc, 30, true, "YOU WON!", 710, 270, Color.GOLD);
		this.writeText(gc, 15, true, "Thanks for playing!", 718, 290, Color.LEMONCHIFFON);
		this.writeText(gc, 15, true, "Press q to quit", 735, 400, Color.WHITE);
		this.writeText(gc, 14, false, "Resets: " + Integer.toString(restarts), 760, 680, Color.WHITE);
	}

	// check if there's another level
	public GameMap getNextLevel() {
		if(nextL)
			return nextLevel;
		return null;
	}


	public void addCurrY(int i)  {
		currY+=i;
	}
}
