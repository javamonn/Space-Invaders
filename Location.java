public class Location {
	private int row; // row location in grid
	private int col; // column location in grid

	public Location(int r, int c) {
		row = r;
		col = c;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Location))
			return false;

		Location otherLoc = (Location) other;
		return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
	}

}
