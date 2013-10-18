
import java.awt.Color;
import java.util.ArrayList;

public class Invader {

	/*
	 * Invader class controls the actual pixel arrangement in the grid for
	 * everything that moves, ie, the player and all enemies. Contains an array
	 * of the pixel class, which are the actual objects in the grid. Note that
	 * an Invader object itself will never be present in the grid, it is just a
	 * useful abstraction for managing all the pixels that would represent the
	 * object.
	 */

	private PixelGrid grid; // we need the grid upon instantiation so we can manage pixels in the grid this class controls
	private String type; // the type of invader, ie, could be the player, or any of the possible enemy types
	private int[][] state1;
	private int[][] state2;
	private int curState;
	private Location loc; // top left location
	private boolean alive;
	private Location bulletLoc; // location of the bullet that killed this invader, kind of a kludge way to remove the invader

	public Invader(PixelGrid gr, String t) {
		grid = gr;
		type = t;
		alive = true;
		init();
	}

	/*
	 * init sets up the pixels in the grid centered around a specified location
	 * and the designated type of the Invader. init will populate the pixels
	 * array and have each pixel add itself to the grid.
	 */
	private void init() {
		if (type.equals("A")) {
			state1 = new int[][] { 
					{ 0, 0, 0, 1, 1, 0, 0, 0 },
					{ 0, 0, 1, 1, 1, 1, 0, 0 }, 
					{ 0, 1, 1, 1, 1, 1, 1, 0 },
					{ 1, 1, 0, 1, 1, 0, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 0, 0, 1, 0, 0, 1, 0, 0 },
					{ 0, 1, 0, 1, 1, 0, 1, 0 },
					{ 1, 0, 1, 0, 0, 1, 0, 1 } };

			state2 = new int[][] { 
					{ 0, 0, 0, 1, 1, 0, 0, 0 },
					{ 0, 0, 1, 1, 1, 1, 0, 0 }, 
					{ 0, 1, 1, 1, 1, 1, 1, 0 },
					{ 1, 1, 0, 1, 1, 0, 1, 1 }, 
					{ 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 0, 1, 0, 1, 1, 0, 1, 0 }, 
					{ 1, 0, 0, 0, 0, 0, 0, 1 },
					{ 0, 1, 0, 0, 0, 0, 1, 0 } };
		} else if (type.equals("B")) {
			state1 = new int[][] { 
					{ 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },
					{ 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
					{ 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
					{ 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
					{ 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
					{ 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0 } };

			state2 = new int[][] { 
					{ 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },
					{ 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1 },
					{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
					{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },
					{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 } };
		} else if (type.equals("C")) {
			state1 = new int[][] { 
					{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0 },
					{ 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0 },
					{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 } };
			state2 = new int[][] { 
					{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1 },
					{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
					{ 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
					{ 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0 },
					{ 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0 } };
		}
		curState = 1;
	}

	// where location is the location at the top left of the bounding grid of
	// the invader
	public synchronized void draw(Location location) {
		if (loc == null) {
			loc = location;
		}
		boolean destroyed = false;
		for (int i = 0; i < state1.length; i++) {
			for (int j = 0; j < state1[0].length; j++) {
				grid.put(new Location(loc.getRow() + i, loc.getCol() + j), Color.BLACK);
			}
		}
		loc = location;
		if (curState == 1) {
			for (int row = 0; row < state1.length; row++) {
				for (int col = 0; col < state1[row].length; col++) {
					if (!destroyed && state1[row][col] == 1) {
						Location temp = new Location(loc.getRow() + row, loc.getCol() + col);
						if (grid.get(temp).equals(Color.BLACK)) {
							grid.put(temp, Color.WHITE);
						} else {
							bulletLoc = temp;
							destroy();
							destroyed = true;
						}
					}
				}
			}
		} else {
			for (int row = 0; row < state2.length; row++) {
				for (int col = 0; col < state2[row].length; col++) {
					if (!destroyed && state2[row][col] == 1) {
						Location temp = new Location(loc.getRow() + row,loc.getCol() + col);
						if (grid.get(temp).equals(Color.BLACK)) {
							grid.put(temp, Color.WHITE);
						} else {
							bulletLoc = temp;
							destroy();
							destroyed = true;
						}
					}
				}
			}
		}
		if (curState == 1) {
			curState = 2;
		} else {
			curState = 1;
		}
	}

	/*
	 * shift will move all the pixels that compose an invader in the direction
	 * specified by the piv. pixels are moved by inc amount of blocks, ie, if
	 * inc=1, each pixel will shift one cell in specified direction. Note that
	 * this method controls all the action, as calls to shift will cause pixels
	 * to move. This method will be called by a Horde object, which controls the
	 * movement of a group of Invaders.
	 */
	public synchronized void shift(String dir, int inc) {
		if (dir.equals("LEFT")) {
			draw(new Location(loc.getRow(), loc.getCol() - inc));
		} else if (dir.equals("RIGHT")) {
			draw(new Location(loc.getRow(), loc.getCol() + inc));
		} else if (dir.equals("DOWN")) {
			draw(new Location(loc.getRow() + inc, loc.getCol()));
		}
	}

	/*
	 * returns an arraylist containing the locations of the pixels that this
	 * Invader manages. Called by horde's hitInvader() to determine which
	 * invader was hit by a bullet.
	 */
	public synchronized ArrayList<Location> getPixelLocs() {
		ArrayList<Location> locs = new ArrayList<Location>();
		if (curState == 1) {
			for (int i = 0; i < state1.length; i++) {
				for (int j = 0; j < state1[i].length; j++) {
					if (state1[i][j] == 1) {
						locs.add(new Location(loc.getRow() + i, loc.getCol() + j));
					}
				}
			}
		} else {
			for (int i = 0; i < state2.length; i++) {
				for (int j = 0; j < state2[i].length; j++) {
					if (state2[i][j] == 1) {
						locs.add(new Location(loc.getRow() + i, loc.getCol() + j));
					}
				}
			}
		}
		return locs;
	}

	/*
	 * destroys this invader. Goes through the pixel array, removing each from
	 * the grid, before setting itself to null.
	 */
	public synchronized void destroy() {
		for (int i = 0; i < state1.length; i++) {
			for (int j = 0; j < state1[i].length; j++) {
				grid.put(new Location(loc.getRow() + i, loc.getCol() + j), Color.BLACK);
			}
		}
		alive = false;
	}

	// upper left location
	public synchronized Location getLocation() {
		return loc;
	}

	public synchronized Location getRightLocation() {
		return new Location(loc.getRow(), loc.getCol() + state1[0].length);
	}

	// upper right location
	public synchronized int getLeftCol() {
		return loc.getCol();
	}

	public synchronized int getRightCol() {
		return loc.getCol() + state1[0].length;
	}

	public synchronized boolean isAlive() {
		return alive;
	}

	public synchronized int getTopRow() {
		return loc.getRow();
	}

	public synchronized int getBottomRow() {
		return loc.getRow() + state1.length;
	}

	public synchronized Location getBulletLoc() {
		return bulletLoc;
	}

	public String getType() {
		return type;
	}

}
