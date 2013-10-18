import java.awt.Color;

public class Barrier {
	
	/* Handles the drawing and managing of the three barriers that sit right above the player on the screen. Most of 
	 * the calls to Barrier objects are going to be through the bulletManager when a bullet detects it has hit a barrier.
	 */
	
	private PixelGrid grid;
	private int[][] state;
	private Location location;

	public Barrier(PixelGrid gr, Location loc) {
		grid = gr;
		location = loc;
		init();
	}

	private void init() {
		state = new int[][] {
				// 10 deep, 19 wide
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 }, };
		draw();
	}

	private void draw() {
		for (int row = 0; row < state.length; row++) {
			for (int col = 0; col < state[row].length; col++) {
				if (state[row][col] == 1) {
					Location temp = new Location(location.getRow() + row, location.getCol() + col);
					if (grid.get(temp).equals(Color.BLACK)) {
						grid.put(temp, Color.GREEN);
					} 
				}
			}
		}
	}

	public boolean containsLoc(Location loc) {
		int row = loc.getRow();
		int col = loc.getCol();
		if ((location.getRow() <= row && location.getCol() <= col)) {
			if ((location.getRow() + state.length >= row) && (location.getCol() + state[0].length >= col)) {
				return true;
			}
		}
		return false;
	}

	//a bullet has hit this barrier, detected at Location loc, traveling direction
	public void hitBarrier(Location loc, String direction) {
		if (direction.equals("SOUTH")) {
			// make sure we are hitting the uppermost loc in the column that we can
			int col = loc.getCol();
			int row = location.getRow();
			while (grid.get(new Location(row, col)).equals(Color.BLACK)) {
				row++;
			}
			loc = new Location(row, col);
			grid.put(loc, Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() - 1), Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() + 1), Color.BLACK);
			grid.put(new Location(loc.getRow() + 1, loc.getCol()), Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() - 2), Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() + 2), Color.BLACK);
			grid.put(new Location(loc.getRow() + 1, loc.getCol() - 1), Color.BLACK);
			grid.put(new Location(loc.getRow() + 1, loc.getCol() + 1), Color.BLACK);
		} else {
			// make sure we are hitting the uppermost loc in the column that we can
			int col = loc.getCol();
			int row = location.getRow() + state.length;
			while (grid.get(new Location(row, col)).equals(Color.BLACK)) {
				row--;
			}
			loc = new Location(row, col);
			grid.put(loc, Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() - 1), Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() + 1), Color.BLACK);
			grid.put(new Location(loc.getRow() - 1, loc.getCol()), Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() - 2), Color.BLACK);
			grid.put(new Location(loc.getRow(), loc.getCol() + 2), Color.BLACK);
			grid.put(new Location(loc.getRow() - 1, loc.getCol() - 1), Color.BLACK);
			grid.put(new Location(loc.getRow() - 1, loc.getCol() + 1), Color.BLACK);
		}
	}

}
