import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;


public class Window extends JComponent implements KeyListener, MouseListener, ActionListener {
	private static final long serialVersionUID = -7269678075128926434L;
	private PixelGrid grid;
	private JFrame frame;
	private int lastKeyPressed;
	private Location lastLocationClicked;

	private boolean gameOver;
	private Timer timer;
	private Clip shootSound;

	private HordeManager hordeManager;
	private BulletManager bulletManager;

	private Barrier[] barriers;
	private Player player;
	private boolean playerWin;

	// create display with each cell of given dimensions
	public Window(int numRows, int numCols) {
		this.grid = new PixelGrid(225, 225);
		lastKeyPressed = -1;
		lastLocationClicked = null;

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				grid.put(new Location(row, col), Color.BLACK);
			}
		}

		frame = new JFrame();
		frame.setTitle("GridDisplay");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);

		// change the constant that gets divided to change the size of the frame
		// that gets displayed. This will also change
		// how much space each cell takes up.
		int cellWidth = 900 / numCols;
		int cellHeight = 900 / numRows;
		int cellSize = Math.min(cellWidth, cellHeight);
		setPreferredSize(new Dimension(numCols * cellSize, numRows * cellSize));
		addMouseListener(this);
		frame.getContentPane().add(this);
		frame.pack();
	}
	
	public void startGame() {
		barriers = new Barrier[3];
		barriers[0] = new Barrier(grid, new Location(170, 42));
		barriers[1] = new Barrier(grid, new Location(170, 103));
		barriers[2] = new Barrier(grid, new Location(170, 164));

		bulletManager = new BulletManager(this);
		hordeManager = new HordeManager(this, bulletManager);
		bulletManager.setHorde(hordeManager.getHorde());

		bulletManager.execute();
		hordeManager.execute();

		player = new Player(grid);
		player.draw(new Location(210, 10));

		// set up sound for player shots
		try {
			File file = new File("invaderkilled.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			shootSound = AudioSystem.getClip();
			shootSound.open(audioIn);
		} catch (Exception e) {
			System.out.println("Sound file not found: invaderkilled.wav");
			System.exit(1);
		}
		gameOver = false;
		timer = new Timer(20, this);
		timer.start();
		frame.setVisible(true);	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int numRows = grid.getNumRows();
		int numCols = grid.getNumCols();
		int cellSize = getCellSize();

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				Location loc = new Location(row, col);
				g.setColor(grid.get(loc));
				int x = col * cellSize;
				int y = row * cellSize;
				g.fillRect(x, y, cellSize, cellSize);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			player.shift("RIGHT", 2);
		}
		if (key == KeyEvent.VK_LEFT) {
			player.shift("LEFT", 2);
		}
		if (key == KeyEvent.VK_UP) {
			if (!bulletManager.isPlayerBulletLive()) {
				Location barrelLoc = player.getBarrelLoc();
				Bullet temp = new Bullet(barrelLoc, grid, "NORTH", 6, 9, true);
				temp.setPlayerBullet();
				bulletManager.addBullet(temp);
				shootSound.setFramePosition(0);
				shootSound.start();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!gameOver) {
			repaint();
			timer.restart();
		} else {
			endGame();
		}
	}

	public void endGame() {
		this.grid = new PixelGrid(125, 125);
		for (int i = 0; i < grid.getNumRows(); i++) {
			for (int j = 0; j < grid.getNumCols(); j++) {
				grid.put(new Location(i, j), Color.BLACK);
			}
		}
		int cellWidth = 900 / 125;
		int cellHeight = 900 / 125;
		int cellSize = Math.min(cellWidth, cellHeight);
		setPreferredSize(new Dimension(125 * cellSize, 125 * cellSize));
		if (!playerWin) {
			DrawString.draw(grid, new Location(20, 25), "GAME OVER");
			DrawString.draw(grid, new Location(35, 25), "SCORE: " + String.valueOf(hordeManager.getScore()));
		} else {
			DrawString.draw(grid, new Location(20, 5), "WELL DONE EARTHLING");
			DrawString.draw(grid, new Location(35, 5), "THIS TIME YOU WIN");
		}
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// ignore
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// ignore
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int cellSize = getCellSize();
		int row = e.getY() / cellSize;
		if (row < 0 || row >= grid.getNumRows()) {
			return;		//ignore
		}
		int col = e.getX() / cellSize;
		if (col < 0 || col >= grid.getNumCols()) {
			return;		//ignore
		}
		lastLocationClicked = new Location(row, col);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// ignore
	}

	public PixelGrid getGrid() {
		return grid;
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}

	// returns -1 if no key pressed since last call.
	// otherwise returns the code for the last key pressed.
	public int checkLastKeyPressed() {
		int key = lastKeyPressed;
		lastKeyPressed = -1;
		return key;
	}

	// returns null if no location clicked since last call.
	public Location checkLastLocationClicked() {
		Location loc = lastLocationClicked;
		lastLocationClicked = null;
		return loc;
	}

	private int getCellSize() {
		int cellWidth = getWidth() / grid.getNumCols();
		int cellHeight = getHeight() / grid.getNumRows();
		return Math.min(cellWidth, cellHeight);
	}

	public Barrier[] getBarriers() {
		return barriers;
	}

	public void gameIsOver(boolean win) {
		playerWin = win;
		hordeManager.gameIsOver();
		bulletManager.gameIsOver();
		gameOver = true;
	}
}
