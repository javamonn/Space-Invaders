import java.awt.Color;

public class Letter {

	private PixelGrid grid;
	private String character;
	private int[][] state;

	private int width;
	private int height;

	public Letter(String ch, PixelGrid gr) {
		character = ch;
		grid = gr;
		init();
	}

	private void init() {
		if (character.equals("A")) {
			state = new int[][] { 
					{ 0, 0, 1, 0, 0 },
					{ 0, 1, 0, 1, 0 },
					{ 0, 1, 0, 1, 0 },
					{ 1, 1, 1, 1, 1 }, 
					{ 1, 0, 0, 0, 1 } };
		} else if (character.equals("B")) {
			state = new int[][] { 
					{ 1, 1, 0, 0 },
					{ 1, 0, 1, 0 },
					{ 1, 1, 1, 1 },
					{ 1, 0, 0, 1 },
					{ 1, 1, 1, 0 } };
		} else if (character.equals("C")) {
			state = new int[][] { 
					{ 0, 1, 1 }, 
					{ 1, 0, 0 }, 
					{ 1, 0, 0 },
					{ 1, 0, 0 }, 
					{ 0, 1, 1 } };
		} else if (character.equals("D")) {
			state = new int[][] { 
					{ 1, 1, 1, 0 }, 
					{ 1, 0, 0, 1 },
					{ 1, 0, 0, 1 },
					{ 1, 0, 0, 1 },
					{ 1, 1, 1, 0 } };
		} else if (character.equals("E")) {
			state = new int[][] { 
					{ 1, 1, 1, 1, 1 }, 
					{ 1, 0, 0, 0, 0 },
					{ 1, 1, 1, 1, 0 },
					{ 1, 0, 0, 0, 0 },
					{ 1, 1, 1, 1, 1 } };
		} else if (character.equals("F")) {
			state = new int[][] { 
					{ 1, 1, 1 }, 
					{ 1, 0, 0 },
					{ 1, 1, 0 },
					{ 1, 0, 0 },
					{ 1, 0, 0 } };
		} else if (character.equals("G")) {
			state = new int[][] { 
					{ 0, 1, 1, 1 }, 
					{ 1, 0, 0, 0 },
					{ 1, 0, 1, 1 }, 
					{ 1, 0, 0, 1 },
					{ 0, 1, 1, 1 } };
		} else if (character.equals("H")) {
			state = new int[][] { 
					{ 1, 0, 0, 1 },
					{ 1, 0, 0, 1 },
					{ 1, 1, 1, 1 },
					{ 1, 0, 0, 1 },
					{ 1, 0, 0, 1 } };
		} else if (character.equals("I")) {
			state = new int[][] { 
					{ 1 }, 
					{ 1 }, 
					{ 1 }, 
					{ 1 }, 
					{ 1 } };
		} else if (character.equals("J")) {
			state = new int[][] { 
					{ 0, 0, 1 }, 
					{ 0, 0, 1 },
					{ 0, 0, 1 },
					{ 1, 0, 1 }, 
					{ 0, 1, 1 } };
		} else if (character.equals("K")) {
			state = new int[][] { 
					{ 1, 0, 0, 1 },
					{ 1, 0, 1, 0 },
					{ 1, 1, 0, 0 },
					{ 1, 0, 1, 1 },
					{ 1, 0, 0, 1 } };
		} else if (character.equals("L")) {
			state = new int[][] { 
					{ 1, 0, 0 },
					{ 1, 0, 0 }, 
					{ 1, 0, 0 },
					{ 1, 0, 0 }, 
					{ 1, 1, 1 } };
		} else if (character.equals("M")) {
			state = new int[][] { 
					{ 1, 0, 0, 0, 1 }, 
					{ 1, 1, 0, 1, 1 },
					{ 1, 0, 1, 0, 1 }, 
					{ 1, 0, 0, 0, 1 }, 
					{ 1, 0, 0, 0, 1 } };
		} else if (character.equals("N")) {
			state = new int[][] { 
					{ 1, 0, 0, 1 }, 
					{ 1, 1, 0, 1 },
					{ 1, 0, 1, 1 }, 
					{ 1, 0, 0, 1 }, 
					{ 1, 0, 0, 1 } };
		} else if (character.equals("O")) {
			state = new int[][] { 
					{ 0, 1, 1, 1, 0, }, 
					{ 1, 0, 0, 0, 1, },
					{ 1, 0, 0, 0, 1, }, 
					{ 1, 0, 0, 0, 1, }, 
					{ 0, 1, 1, 1, 0, } };
		} else if (character.equals("P")) {
			state = new int[][] { 
					{ 1, 1, 1, 0 }, 
					{ 1, 0, 0, 1 },
					{ 1, 1, 1, 1 }, 
					{ 1, 0, 0, 0 }, 
					{ 1, 0, 0, 0 } };
		} else if (character.equals("Q")) {
			state = new int[][] { 
					{ 0, 1, 1, 0 },
					{ 1, 0, 0, 1 },
					{ 1, 0, 0, 1 },
					{ 1, 0, 1, 0 },
					{ 0, 1, 0, 1 } };
		} else if (character.equals("R")) {
			state = new int[][] { 
					{ 1, 1, 1, 0 }, 
					{ 1, 0, 0, 1 },
					{ 1, 1, 1, 1 }, 
					{ 1, 0, 1, 0 }, 
					{ 1, 0, 0, 1 } };
		} else if (character.equals("S")) {
			state = new int[][] { 
					{ 0, 1, 1, 1 }, 
					{ 1, 0, 0, 0 },
					{ 1, 1, 1, 1 }, 
					{ 0, 0, 0, 1 }, 
					{ 1, 1, 1, 0 } };
		} else if (character.equals("T")) {
			state = new int[][] { 
					{ 1, 1, 1 }, 
					{ 0, 1, 0 }, 
					{ 0, 1, 0 },
					{ 0, 1, 0 }, 
					{ 0, 1, 0 } };
		} else if (character.equals("U")) {
			state = new int[][] { 
					{ 1, 0, 0, 1 }, 
					{ 1, 0, 0, 1 },
					{ 1, 0, 0, 1 }, 
					{ 1, 0, 0, 1 }, 
					{ 0, 1, 1, 1 } };
		} else if (character.equals("V")) {
			state = new int[][] { 
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 1, 0, 1, 0 },
					{ 0, 1, 0, 1, 0 },
					{ 0, 0, 1, 0, 0 } };
		} else if (character.equals("W")) {
			state = new int[][] { 
					{ 1, 0, 0, 0, 1 }, 
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 1, 0, 1 }, 
					{ 1, 0, 1, 0, 1 }, 
					{ 0, 1, 0, 1, 0 } };
		} else if (character.equals("X")) {
			state = new int[][] { 
					{ 1, 0, 0, 0, 1 }, 
					{ 0, 1, 0, 1, 0 },
					{ 0, 0, 1, 0, 0 }, 
					{ 0, 1, 0, 1, 0 }, 
					{ 1, 0, 0, 0, 1 } };
		} else if (character.equals("Y")) {
			state = new int[][] { 
					{ 1, 0, 0, 0, 1 }, 
					{ 0, 1, 0, 1, 0 },
					{ 0, 0, 1, 0, 0 }, 
					{ 0, 0, 1, 0, 0 }, 
					{ 0, 0, 1, 0, 0 } };
		} else if (character.equals("Z")) {
			state = new int[][] { 
					{ 1, 1, 1, 1 }, 
					{ 1, 0, 0, 1 },
					{ 0, 0, 1, 0 }, 
					{ 0, 1, 0, 0 }, 
					{ 1, 1, 1, 1 } };
		} else if (character.equals(":")) {
			state = new int[][] { 
					{ 1 }, 
					{ 1 }, 
					{ 0 },
					{ 1 },
					{ 1 }, };
		} else if (character.equals(" ")) {
			state = new int[][] { 
					{ 0, 0, 0, 0 },
					{ 0, 0, 0, 0 },
					{ 0, 0, 0, 0 },
					{ 0, 0, 0, 0 }, 
					{ 0, 0, 0, 0 } };
		} else if (character.equals("0")) {
			state = new int[][] { 
					{ 0, 1, 1, 1, 0 }, 
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 }, 
					{ 1, 0, 0, 0, 1 }, 
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 }, 
					{ 1, 0, 0, 0, 1 }, 
					{ 0, 1, 1, 1, 0 } };
		} else if (character.equals("1")) {
			state = new int[][] { 
					{ 0, 1, 0 }, 
					{ 1, 1, 0 }, 
					{ 0, 1, 0 },
					{ 0, 1, 0 }, 
					{ 0, 1, 0 }, 
					{ 0, 1, 0 }, 
					{ 0, 1, 0 },
					{ 1, 1, 1 } };
		} else if (character.equals("2")) {
			state = new int[][] { 
					{ 0, 1, 1, 1, 0 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 0, 0, 0, 1 },
					{ 0, 0, 0, 1, 0 },
					{ 0, 0, 1, 0, 0 },
					{ 0, 1, 0, 0, 0 },
					{ 1, 0, 0, 0, 0 },
					{ 1, 1, 1, 1, 1 }, };
		} else if (character.equals("3")) {
			state = new int[][] { 
					{ 0, 1, 1, 1, 0 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 0, 0, 0, 1 },
					{ 0, 0, 1, 1, 0 },
					{ 0, 0, 0, 0, 1 },
					{ 0, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 1, 1, 1, 0 }, };
		} else if (character.equals("4")) {
			state = new int[][] { 
					{ 0, 0, 0, 1, 0 },
					{ 0, 0, 1, 1, 0 },
					{ 0, 1, 0, 1, 0 },
					{ 1, 0, 0, 1, 0 },
					{ 1, 1, 1, 1, 1 },
					{ 0, 0, 0, 1, 0 },
					{ 0, 0, 0, 1, 0 }, 
					{ 0, 0, 0, 1, 0 }, };
		} else if (character.equals("5")) {
			state = new int[][] { 
					{ 1, 1, 1, 1, 1 },
					{ 1, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 0 },
					{ 1, 1, 1, 1, 0 },
					{ 0, 0, 0, 0, 1 },
					{ 0, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 1, 1, 1, 0 }, };
		} else if (character.equals("6")) {
			state = new int[][] { 
					{ 0, 0, 1, 1, 0 },
					{ 0, 1, 0, 0, 0 },
					{ 1, 0, 0, 0, 0 },
					{ 1, 1, 1, 1, 0 },
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 1, 1, 1, 0 }, };
		} else if (character.equals("7")) {
			state = new int[][] { 
					{ 1, 1, 1, 1, 1 },
					{ 0, 0, 0, 0, 1 },
					{ 0, 0, 0, 1, 0 },
					{ 0, 0, 0, 1, 0 },
					{ 0, 0, 1, 0, 0 },
					{ 0, 0, 1, 0, 0 },
					{ 0, 1, 0, 0, 0 },
					{ 0, 1, 0, 0, 0 }, };
		} else if (character.equals("8")) {
			state = new int[][] { 
					{ 0, 1, 1, 1, 0 },
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 1, 1, 1, 0 },
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 1, 1, 1, 0 }, };
		} else if (character.equals("9")) {
			state = new int[][] { 
					{ 0, 1, 1, 1, 0 },
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 1 },
					{ 0, 1, 1, 1, 1 },
					{ 0, 0, 0, 0, 1 },
					{ 0, 0, 0, 1, 0 },
					{ 0, 1, 1, 0, 0 }, };
		}
		width = state[0].length;
		height = state.length;
	}

	public void draw(Location loc) {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (state[row][col] == 1) {
					grid.put(new Location(loc.getRow() + row, loc.getCol() + col), Color.WHITE);
				}
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
