import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingWorker;

public class HordeManager extends SwingWorker<Void, PixelGrid> {

	private Window display;
	private int score;
	private boolean gameOver;
	private PixelGrid grid;
	
	private Clip sound1;
	private Clip sound2;
	private Clip sound3;
	private Clip sound4;
	private int curSound;
	

	private Horde horde;
	private BulletManager bulletManager;

	public HordeManager(Window dis, BulletManager bullets) {
		display = dis;
		bulletManager = bullets;
		score = 0;
		gameOver = false;
		grid = display.getGrid();
		try {
			File file;
			AudioInputStream audioIn;
			
			file = new File("invader1.wav");
			audioIn = AudioSystem.getAudioInputStream(file);
			sound1 = AudioSystem.getClip();
			sound1.open(audioIn);
			file = new File("invader2.wav");
			audioIn = AudioSystem.getAudioInputStream(file);
			sound2 = AudioSystem.getClip();
			sound2.open(audioIn);
			file = new File("invader3.wav");
			audioIn = AudioSystem.getAudioInputStream(file);
			sound3 = AudioSystem.getClip();
			sound3.open(audioIn);
			file = new File("invader4.wav");
			audioIn = AudioSystem.getAudioInputStream(file);
			sound4 = AudioSystem.getClip();
			sound4.open(audioIn);
			curSound = 1;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		horde = new Horde(this, bulletManager);
	}

	@Override
	protected Void doInBackground() {
		try {
			int delay = 500;
			int nanos = 0;
			while (!gameOver) {
				setProgress(0);
				if (delay > 20) {
					if (nanos == 0) { 
						nanos = 500;
					} else {
						nanos = 0;
					}
				}
				if (horde.move() == 0) {
					horde = new Horde(this, bulletManager);
					score += 500;
				}
				switch (curSound) {
					case (1) :
						sound1.setFramePosition(0);
						sound1.start();
						curSound++;
						break;
					case (2) :
						sound2.setFramePosition(0);
						sound2.start();
						curSound++;
						break;
					case (3) :
						sound3.setFramePosition(0);
						sound3.start();
						curSound++;
						break;
					case (4) :
						sound4.setFramePosition(0);
						sound4.start();
						curSound = 1;
						break;
				}
				updateTitle();
				if (delay > 20) {
					if (nanos == 500) {
						delay-= 2;
					} else {
						delay--;
					}
				}
				publish(grid);
				Thread.sleep(delay, nanos);
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		return null;
	}

	@Override
	protected void process(List<PixelGrid> list) {
		display.repaint();
	}

	public void updateTitle() {
		score += horde.getScore();
		display.setTitle("Score:  " + score);
	}

	public int getScore() {
		return score;
	}

	public PixelGrid getGrid() {
		return grid;
	}

	public Horde getHorde() {
		return horde;
	}
	
	public void gameIsOver() {
		gameOver = true;
	}
	
	public void hordeReportsGameOver() {
		display.gameIsOver(true);
	}

}
