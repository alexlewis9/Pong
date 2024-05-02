/*Name: Sarah Sabaa & Irena Liu
 *Date: 5/20/2020
 *Rev: Production Release
 *Notes: This class creates paddle that moves in two different levels.
 *
 */

import java.awt.Graphics;

public class Paddle {
	public int x;
	public int y;
	private int width;
	private int height;
	public final int PADDLE_WIDTH = 20;
	public final int PADDLE_HEIGHT = 90;
	
	public Paddle(int w, int h, int margin) {
		this.width = w;
		this.height = h;
		x = width - PADDLE_WIDTH - margin;
		y = height/2 - PADDLE_HEIGHT/2;

	}
	
	public void paint(Graphics g) {
		g.fillRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		
	}
	
	public void move(int move) {
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void pressed(int d) {
		// for keyboard paddle
	}
	
	public void released() {
		// for keyboard paddle
	}
}
