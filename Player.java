import java.awt.Color;

public class Player {

	private PixelGrid grid; // we need the grid upon instantiation so we can manage pixels in the grid this class controls
	private Location barrelLoc; // location for the barrel (1 row above it, really) so we know where bullet object should be spawned
	private int[][] state;
	private Location location;

	public Player(PixelGrid gr) {
		grid = gr;
		state = new int[][] { 
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
	}

	/*
	 * called when game detects a left or right arrow press. First check if
	 * player can move left/right, then shift all the pixels in the Pixel array
	 * over if were not on the edge. If we are on the edge and cant move in the
	 * desired direction, we ignore the keypress.
	 */
	public void draw(Location loc) {
		location = loc;
		barrelLoc = new Location(loc.getRow() - 1, loc.getCol() + 7);
		for (int row = 0; row < state.length; row++) {
			for (int col = 0; col < state[row].length; col++) {
				if (state[row][col] == 1) {
					Color color = new Color(0, 255, 0);
					grid.put(new Location(location.getRow() + row, location.getCol() + col), color);
				}
			}
		}
	}

	public void shift(String dir, int inc) {
		for (int row = 0; row < state.length; row++) {
			for (int col = 0; col < state[row].length; col++) {
				grid.put(new Location(location.getRow() + row, location.getCol()+ col), Color.BLACK);
			}
		}
		if (dir.equals("LEFT")) {
			Location newLoc = new Location(location.getRow(), location.getCol() - inc);
			if (newLoc.getCol() >= 3) {
				draw(newLoc);
			} else {
				draw(location);
			}
		} else if (dir.equals("RIGHT")) {
			Location newLoc = new Location(location.getRow(), location.getCol() + inc);
			if (newLoc.getCol() + 18 <= grid.getNumCols()) {
				draw(newLoc);
			} else {
				draw(location);
			}
		}
	}

	public Location getBarrelLoc() {
		return barrelLoc;
	}

	public void destroy() {
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				grid.put(new Location(location.getRow() + i, location.getCol()+ j), Color.BLACK);
			}
		}
	}
}
