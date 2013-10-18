import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingWorker;

public class BulletManager extends SwingWorker<Void, PixelGrid> {

	private Window display;
	private Horde horde;
	private PixelGrid grid;
	private Clip killSound;
	private ArrayList<Bullet> bullets;
	private boolean playerBulletLive;
	private boolean gameOver;

	public BulletManager(Window dis) {
		playerBulletLive = false;
		bullets = new ArrayList<Bullet>();
		gameOver = false;
		display = dis;
		
		try {
			File file = new File("explosion.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			killSound = AudioSystem.getClip();
			killSound.open(audioIn);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		grid = display.getGrid();
	}

	@Override
	protected Void doInBackground() {
		try {
			int msElapsed = 0;
			while (!gameOver) {
				// move bullets in their respective directions every time through the loop
				for (int i = 0; i < bullets.size(); i++) {
					if (bullets.get(i).isLive()) {
						Location loc = bullets.get(i).shift();
						if (loc != null) {
							// check to see if bullet hit a barrier
							Barrier[] barriers = new Barrier[3];
							barriers = display.getBarriers();
							boolean handled = false;
							for (int j = 0; j < 3; j++) {
								if (barriers[j].containsLoc(loc)) {
									barriers[j].hitBarrier(loc, bullets.get(i).getDirection());
									handled = true;
								}
							}
							if (!handled
									&& bullets.get(i).getDirection()
											.equals("SOUTH")
									&& bullets.get(i).getLocation().getRow() >= 205) {
								// if the location wasnt a barrier, a bullet hit the player, game is now over
								killSound.setFramePosition(0);
								killSound.start();
								display.gameIsOver(false);
								gameOver = true;
							} else {
								horde.hitInvader(loc); // if shift actually returned a location, its the loc of a collision
							}
						}
					}
					if (!bullets.get(i).isLive()) {
						// check to see if the bullet is no longer live, remove it if so
						if (bullets.get(i).isPlayerBullet()) {
							playerBulletLive = false;
						}
						bullets.remove(i);
					}
				}
				if (msElapsed % 1000 == 0) {
					bullets.addAll(horde.shoot());
				}
				msElapsed += 50;
				setProgress(100);
				publish(grid);
				Thread.sleep(50);
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

	public void addBullet(Bullet bullet) {
		if (bullet.isPlayerBullet()) {
			playerBulletLive = true;
		}
		bullets.add(bullet);
	}

	public boolean isPlayerBulletLive() {
		return playerBulletLive;
	}

	public PixelGrid getGrid() {
		return grid;
	}

	public Bullet getBulletContaining(Location loc) {
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).contains(loc)) {
				if (bullets.get(i).isPlayerBullet()) {
					playerBulletLive = false;
				}
				return bullets.get(i);
			}
		}
		return null;
	}

	public void setHorde(Horde hr) {
		horde = hr;
	}
	public void gameIsOver() {
		gameOver = true;
	}
}
