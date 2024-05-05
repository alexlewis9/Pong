// This class moves the player paddle based on the keys the user presses.

public class PlayerPaddleKeys extends Paddle {
	
	private int yy = 0;
	
	public PlayerPaddleKeys(int w, int h, int margin, int pNum) {
		super(w, h, margin);
		if (pNum == 2) {
			x = margin;
		}
	}

	public void move(int move) {
		y += yy;
		if (y < 0) {
			y = 0;
		}
		if (y > this.getHeight() - 90) {
			y = this.getHeight() - 90;
		}
	}
	
	public void pressed(int d) {
		if (d == 0) {
			yy = -2;
		} else if (d == 1) {
			yy = 2;
		}
	}
	
	public void released() {
		yy = 0;
	}

}
