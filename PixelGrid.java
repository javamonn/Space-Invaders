import java.awt.Color;

public class PixelGrid {
	
	private Color[][] pixels; // the array storing the grid elements

	public PixelGrid(int rows, int cols) {
		if (rows <= 0)
			throw new IllegalArgumentException("rows <= 0");
		if (cols <= 0)
			throw new IllegalArgumentException("cols <= 0");
		pixels = new Color[rows][cols];
	}


	public int getNumRows() {
		return pixels.length;
	}

	public int getNumCols() {
		// Note: according to the constructor precondition, numRows() > 0, so
		// theGrid[0] is non-null.
		return pixels[0].length;
	}

	public boolean isValid(Location loc) {
		return 0 <= loc.getRow() && loc.getRow() < getNumRows()
				&& 0 <= loc.getCol() && loc.getCol() < getNumCols();
	}

	public Color get(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc + " is not valid");
		}
		return pixels[loc.getRow()][loc.getCol()]; 
	}

	public void put(Location loc, Color col) {
		pixels[loc.getRow()][loc.getCol()] = col;
	}

	public void remove(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc + " is not valid");
		}
		pixels[loc.getRow()][loc.getCol()] = null;
	}

}
