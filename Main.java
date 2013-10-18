import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(new Runnable() {
			Window display;
			@Override
			public void run() {
				display = new Window(225, 225);
				display.startGame();
			}
		});
	}
}
