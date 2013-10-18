import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Horde {

	private Invader[][] invaders; // array of Invader objects that are in the Horde
	private PixelGrid grid; // grid that contains the Invaders
	private int direction; // direction that the horde is currently shifting. (180 = left, 0 = right)
	private int score;
	private Clip killSound;
	
	private boolean gameOver;
	private HordeManager hordeManager;
	private BulletManager bulletManager;

	public Horde(HordeManager manager, BulletManager bullets) {
		hordeManager = manager;
		bulletManager = bullets;
		invaders = new Invader[4][11]; // assume size is 11 for now, standard
		grid = manager.getGrid();
		direction = 180;
		score = 0;
		gameOver = false;
		
		try {
			File file = new File("shoot.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			killSound = AudioSystem.getClip();
			killSound.open(audioIn);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		init(); // other constructor operations, moved so we dont bloat the code here

	}

	/*
	 * fills the grid with invader objects.
	 */
	private void init() {
		for (int row = 0; row < invaders.length; row++) {
			for (int col = 0; col < invaders[row].length; col++) {
				if (row == 0) {
					int colSpace = grid.getNumCols() - 88;
					int invaderGap = 8;
					int wallGap = (colSpace - (invaderGap * 10)) / 2;
					invaders[row][col] = new Invader(grid, "A");
					invaders[row][col].draw(new Location(3, wallGap + (invaderGap + 8) * col));
				} else if (row == 1) {
					int colSpace = grid.getNumCols() - 121;
					int invaderGap = 5;
					int wallGap = (colSpace - (invaderGap * 10)) / 2;
					invaders[row][col] = new Invader(grid, "B");
					invaders[row][col].draw(new Location(16, wallGap + (invaderGap + 11) * col));
				} else if (row == 2) {
					int colSpace = grid.getNumCols() - 121;
					int invaderGap = 5;
					int wallGap = (colSpace - (invaderGap * 10)) / 2;
					invaders[row][col] = new Invader(grid, "B");
					invaders[row][col].draw(new Location(29, wallGap + (invaderGap + 11) * col));
				} else {
					int colSpace = grid.getNumCols() - 132;
					int invaderGap = 4;
					int wallGap = (colSpace - (invaderGap * 10)) / 2;
					invaders[row][col] = new Invader(grid, "C");
					invaders[row][col].draw(new Location(42, wallGap + (invaderGap + 12) * col));
				}

			}
		}
	}

	/*
	 * Called when a bullet shot by the player detects a collision in the cell
	 * it is moving into. The bullet will call this method with the location in
	 * which it detected the collision. hitInvader will take this location, and
	 * loop through the invaders array calling pixelLocs() on each to check
	 * which invader contains a pixel that occupies the location the collision
	 * was detected in. This invader is now "destroyed", so it will be removed
	 * from both the array and the grid.
	 */
	public void hitInvader(Location loc) {
		for (int row = 0; row < invaders.length; row++) {
			for (int col = 0; col < invaders[row].length; col++) {
				if (invaders[row][col] != null) {
					Invader temp = invaders[row][col];
					if (loc.getRow() >= temp.getTopRow() && loc.getRow() <= temp.getBottomRow()) {
						if (loc.getCol() >= temp.getLeftCol() && loc.getCol() <= temp.getRightCol()) {
							if (temp.getType().equals("A")) {
								addToScore(40);
							} else if (temp.getType().equals("B")) {
								addToScore(20);
							} else if (temp.getType().equals("C")) {
								addToScore(10);
							}
							killSound.setFramePosition(0);
							killSound.start();
							temp.destroy(); // tell invader to remove from the grid all the pixels it manages
							invaders[row][col] = null; // remove this invader from the list
							return;
						}
					}
				}
			}
		}

	}

	/*
	 * Move will loop through invaders list and call shift on each invader with
	 * the current direction to move all the invaders. after everyone moves, we
	 * call updateDir to check if the horde is now on an edge and as such, our
	 * direction should be changed
	 * 
	 * Right now move works by removing each invader from the grid, and then
	 * redrawing it in it's new location. I'm unsure as to whether there is a
	 * better way to implement this process.
	 */
	public int move() {
		int invadersLeft = 0; // what this method returns, 1 if there are still invaders left, 0 otherwise. So hordemanager knows when to instantiate a new one
		if (direction == 180) {
			Location edge = getLeftEdge();
			// reached the left edge, should move down now
			if (edge.getCol() < 5) {
				for (int i = 3; i >= 0; i--) { // move the invaders the farthest down first to make room for others 
					for (int j = 10; j >= 0; j--) {
						if (invaders[i][j] != null) {
							Invader temp = invaders[i][j];
							temp.draw(new Location(temp.getLocation().getRow() + 16, temp.getLocation().getCol()));
							if (!temp.isAlive()) {
								bulletManager.getBulletContaining(temp.getBulletLoc()).destroy();
								if (temp.getType().equals("A")) {
									addToScore(40);
								} else if (temp.getType().equals("B")) {
									addToScore(20);
								} else if (temp.getType().equals("C")) {
									addToScore(10);
								}
								temp = null;
								invaders[i][j] = null;
							} else {
								invadersLeft = 1;
							}
						}
					}
				}
				direction = 0;
			} else {
				for (int i = 0; i < invaders.length; i++) {
					for (int j = 0; j < invaders[i].length; j++) {
						if (invaders[i][j] != null) {
							Invader temp = invaders[i][j];
							temp.draw(new Location(temp.getLocation().getRow(), temp.getLocation().getCol() - 1));
							if (!temp.isAlive()) {
								bulletManager.getBulletContaining(temp.getBulletLoc()).destroy();
								if (temp.getType().equals("A")) {
									addToScore(40);
								} else if (temp.getType().equals("B")) {
									addToScore(20);
								} else if (temp.getType().equals("C")) {
									addToScore(10);
								}
								temp = null;
								invaders[i][j] = null;
							} else {
								invadersLeft = 1;
							}
						}
					}
				}
			}
		} else if (direction == 0) {
			/* check to make sure we are not on an edge */
			Location edge = getRightEdge();
			if (edge.getCol() >= grid.getNumCols() - 5) {
				for (int i = 3; i >= 0; i--) {
					for (int j = 10; j >= 0; j--) {
						if (invaders[i][j] != null) {
							Invader temp = invaders[i][j];
							temp.draw(new Location(temp.getLocation().getRow() + 16, temp.getLocation().getCol()));
							if (!temp.isAlive()) {
								bulletManager.getBulletContaining(temp.getBulletLoc()).destroy();
								if (temp.getType().equals("A")) {
									addToScore(40);
								} else if (temp.getType().equals("B")) {
									addToScore(20);
								} else if (temp.getType().equals("C")) {
									addToScore(10);
								}
								invaders[i][j] = null;
								temp = null;
							} else {
								invadersLeft = 1;
							}
						}
					}
				}
				direction = 180;
			} else {
				for (int i = 0; i < invaders.length; i++) {
					for (int j = 0; j < invaders[i].length; j++) {
						if (invaders[i][j] != null) {
							Invader temp = invaders[i][j];
							temp.draw(new Location(temp.getLocation().getRow(), temp.getLocation().getCol() + 1));
							if (!temp.isAlive()) {
								bulletManager.getBulletContaining(temp.getBulletLoc()).destroy();
								if (temp.getType().equals("A")) {
									addToScore(40);
								} else if (temp.getType().equals("B")) {
									addToScore(20);
								} else if (temp.getType().equals("C")) {
									addToScore(10);
								}
								invaders[i][j] = null;
								temp = null;
							} else
								invadersLeft = 1;
						}
					}
				}
			}
		}
		return invadersLeft;
	}

	public ArrayList<Bullet> shoot() {
		int[] columnHead = new int[11]; // the row number of the invader at the head of the column, -1 if doesn't exist
		for (int i = 0; i < 11; i++) {
			columnHead[i] = 4;
		}
		for (int col = 0; col < 11; col++) {
			for (int row = 3; row >= 0; row--) {
				if (invaders[row][col] != null) {
					columnHead[col] = row;
					break;
				}
			}
			if (columnHead[col] == 4) {
				columnHead[col] = -1;
			}
		}
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		for (int i = 0; i < 11; i++) {
			double chance = columnHead[i] / 30.0;
			double roll = Math.random();
			if (roll <= chance) {
				Invader head = invaders[columnHead[i]][i];
				Location bulletLoc = new Location(0, 0);
				if (head.getType().equals("A")) {
					bulletLoc = new Location(head.getLocation().getRow() + 8, head.getLocation().getCol() + 4);
				} else if (head.getType().equals("B")) {
					bulletLoc = new Location(head.getLocation().getRow() + 8, head.getLocation().getCol() + 5);
				} else if (head.getType().equals("C")) {
					bulletLoc = new Location(head.getLocation().getRow() + 8, head.getLocation().getCol() + 5);
				}
				Bullet bullet = new Bullet(bulletLoc, grid, "SOUTH", 6, 6, false);
				bullets.add(bullet);
			}
		}
		return bullets;

	}

	/*
	 * loops through the invaders array, looking for the first non null invader
	 * reference that is the farthest left. This is used to determine when the
	 * horde should move down instead of left for the next call to move.
	 */
	public Location getLeftEdge() {
		for (int col = 0; col < 11; col++) {
			for (int row = 0; row < 4; row++) {
				if (invaders[row][col] != null) {
					return invaders[row][col].getLocation();
				}
			}
		}
		//if we get down here, the game is over, player has won
		hordeManager.hordeReportsGameOver();
		return null;
	}

	/*
	 * loops through the invaders array, looking for the first non null invader
	 * reference that is the right most column. This is used for determining
	 * when the horde has reached the right edge of the screen and should move
	 * down instead of right for the next call to move.
	 */
	public Location getRightEdge() {
		for (int col = 10; col >= 0; col--) {
			for (int row = 0; row < 4; row++) {
				if (invaders[row][col] != null) {
					return invaders[row][col].getRightLocation();
				}
			}
		}
		//if we get down here, the game is over, player has won
		hordeManager.hordeReportsGameOver();
		return null;
	}

	public Invader[][] getInvaders() {
		return invaders;
	}

	public void destroy() {
		for (int i = 0; i < invaders.length; i++) {
			for (int j = 0; j < invaders[0].length; j++) {
				if (invaders[i][j] != null) {
					invaders[i][j].destroy();
				}
			}
		}
	}

	/*
	 * since horde is the only object that can tell which invaders have been
	 * destroyed, we keep score here and return the value for the hordemanager
	 * thread to keep track of it. returns the points earned since method was
	 * last called.
	 */
	public int getScore() {
		int temp = score;
		score = 0;
		return temp;
	}

	public void addToScore(int add) {
		score += add;
	}

	public void gameOver() {
		gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public PixelGrid getGrid() {
		return grid;
	}
	
}
