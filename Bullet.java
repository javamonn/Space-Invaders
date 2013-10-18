import java.awt.Color;

public class Bullet {

	private PixelGrid grid;
	private Location location; // location that the head of the bullet currently occupies
	private String direction;  // direction of bullet, either NORTH or SOUTH, (90 = north, 270 = south)
	private Location[] locs;   // index 3 is leading pixel
	private int length;
	private int speed;
	private boolean live;
	private boolean playerBullet;
	private boolean isGreen;

	public Bullet(Location loc, PixelGrid gr, String dir, int ln, int sp, boolean green) {
		grid = gr;
		locs = new Location[ln];
		length = ln;
		location = loc;
		direction = dir;
		speed = sp;
		isGreen = green;
		if (dir.equals("NORTH")) {
			for (int i = 0; i < length; i++) {
				locs[i] = new Location(location.getRow() - i, location.getCol());
				if (!isGreen) {
					grid.put(locs[i], Color.WHITE);
				} else {
					grid.put(locs[i], Color.GREEN);
				}
			}
		}
		if (dir.equals("SOUTH")) {
			for (int i = 0; i < length; i++) {
				locs[i] = new Location(location.getRow() + i, location.getCol());
				if (!isGreen) {
					grid.put(locs[i], Color.WHITE);
				} else {
					grid.put(locs[i], Color.GREEN);
				}
			}
		}
		location = locs[locs.length - 1];
		live = true;
		playerBullet = false;
	}

	/*
	 * Moves the bullet if possible. If bullet hits an invader, we return the
	 * location of the collision to the bullet manager so it can call the
	 * hitInvader() method. This avoids having to pass a horde reference to each
	 * bullet instance. If we could shift without issue or if the bullet shifts
	 * off the screen, return a null location. This is probably a shit
	 * programming practice but it's a viable solution at this point.
	 */
	public synchronized Location shift() {
		if (!grid.isValid(locs[locs.length - 1]))
			live = false;

		for (int i = 0; i < length; i++) {
			grid.put(locs[i], Color.BLACK);
		}
		Location[] newLocations = new Location[length];
		boolean canMove = true;
		Location collisionLoc = null;
		if (direction.equals("NORTH")) {
			for (int i = 0; canMove && i < length; i++) {
				newLocations[i] = new Location(locs[i].getRow() - speed, locs[i].getCol());
				if (!grid.isValid(newLocations[i])) {
					canMove = false;
				} else if (!grid.get(newLocations[i]).equals(Color.BLACK)) {
					canMove = false;
					collisionLoc = newLocations[i];
				}
			}
		} else if (direction.equals("SOUTH")) {
			for (int i = 0; canMove && i < length; i++) {
				newLocations[i] = new Location(locs[i].getRow() + speed, locs[i].getCol());
				if (!grid.isValid(newLocations[i])) {
					canMove = false;
				} else if (!grid.get(newLocations[i]).equals(Color.BLACK)) {
					canMove = false;
					collisionLoc = newLocations[i];
				}
			}
		}
		if (canMove) {
			for (int i = 0; i < length; i++) {
				if (isGreen) {
					grid.put(newLocations[i], Color.GREEN);
				} else {
					grid.put(newLocations[i], Color.WHITE);
				}
			}
			locs = newLocations;
			location = locs[locs.length - 1];
			return null;
		} else {
			live = false;
			return collisionLoc;
		}
	}

	public boolean isLive() {
		return live;
	}

	public Location getLocation() {
		return location;
	}

	public void destroy() {
		live = false;
		for (int i = 0; i < length; i++) {
			grid.put(locs[i], Color.BLACK);
		}
	}

	public String getDirection() {
		return direction;
	}

	public Location[] getLocs() {
		return locs;
	}

	public void setPlayerBullet() {
		playerBullet = true;
	}

	public boolean isPlayerBullet() {
		return playerBullet;
	}

	public boolean contains(Location loc) {
		for (int i = 0; i < length; i++) {
			if (locs[i].getRow() == loc.getRow() && locs[i].getCol() == loc.getCol()) {
				return true;
			}
		}
		return false;
	}

	public void setGreen() {
		isGreen = true;
	}
}
