
public class DrawString {
/*
	public Letter[] letters;

	public DrawString(PixelGrid grid, Location location, String string) {
		letters = new Letter[string.length()];
		draw(grid, location, string);
	}
*/
	public static void draw(PixelGrid grid, Location location, String string) {
		int buffer = 0;
		for (int i = 0; i < string.length(); i++) {
			String substring = string.substring(i, i + 1);
			Letter letter = new Letter(substring, grid);
			if (letter.getHeight() == 8) {
				letter.draw(new Location(location.getRow() - 3, location.getCol() + (i + 1) + buffer));
			} else {
				letter.draw(new Location(location.getRow(), location.getCol() + (i + 1) + buffer));
			}
		//	letters[i] = letter;
			buffer += letter.getWidth();
		}
	}
}
