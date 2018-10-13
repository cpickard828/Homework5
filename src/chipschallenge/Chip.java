package chipschallenge;

// Cameron Pickard

import java.awt.Point;


import java.util.Observable;

public class Chip extends Observable {
	int xCell;
	int yCell;
	int unitSize;  // Scaling factor
	GameMap map;

	public Chip(int x, int y, int unitSize, GameMap map){
		xCell = x;
		yCell = y;
		this.unitSize = unitSize;
		this.map = map; // We need a reference to it so we can access the grids!
	}

	// Return point coordinate of chip
	public Point getLocation(){
		Point position = new Point(this.xCell, this.yCell);
		return position;
	}
	// Movement functions
	public boolean goEast() {
		if(this.xCell < 24 && map.seeSpace(this.xCell + 1, this.yCell) != 1 ) {

			return true;
		}
		return false;

	}
	public boolean goWest() {
		if(this.xCell>0 && map.seeSpace(this.xCell - 1, this.yCell)!= 1 ) {
			return true;
		}
		return false;
	}
	public boolean goSouth() {
		if(this.yCell < 24 && map.seeSpace(this.xCell, this.yCell+1) != 1) {
			return true;
		}
		return false;
	}
	public boolean goNorth() {
		if(this.yCell > 0 && map.seeSpace(this.xCell, this.yCell-1) != 1) {
			return true;
		}
		return false;
	}

	public void setPos(int x, int y) {
		this.xCell = x;
		this.yCell = y;
		return;
	}
}
