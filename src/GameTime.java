// This class creates a timer used when playing unbeatable mode.

import java.util.Timer;
import java.util.TimerTask;

public class GameTime {

	private int seconds = 0;
	private Timer myTimer = new Timer();
	private TimerTask task = new TimerTask() {
			public void run() {
				seconds ++;
			}
	};
		
	public GameTime() {
		
	}

	public void start() {
		myTimer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	
	public int getSeconds() {
		return seconds;
	}
	
	public void endTimer() {
		myTimer.cancel();
	}
	
}

