// This class creates a ball that moves at random speeds and reflects off paddles.

import java.awt.Graphics;

public class Ball {

	private int x;
	private int y;
	private int width;
	private int height;
	private int direction;
	private int xMove = (int) (Math.random()*3) + 1;//speed
	private int yMove = (int) (Math.random()*3) + 1;
	public final int BALL_WIDTH = 20;
	public final int BALL_HEIGHT = 20;
	
	public Ball(int width, int height) {
		this.width = width;
		this.height = height;
		x = width/2 - BALL_WIDTH/2;
		y = height/2 - BALL_HEIGHT/2;
	}
	
	public void paint(Graphics g) {
		g.fillOval(x, y, BALL_WIDTH, BALL_HEIGHT);
	}
	
	public void move() {
		x += xMove;
		y += yMove;
		if (y <= 0 || y >= height - 40) {
			yMove = - yMove;
		}
	}
	
	public void hitPaddle() { //ball reflects when it hits a paddle
		direction = (int)(Math.random()*2);
		if (direction == 0) {
			direction = -1;
		}
		xMove = - xMove;
		yMove = direction * (int)((Math.random()*2) + 1);
	}
	
	public void serve(int d) {
		x = width/2 - BALL_WIDTH/2;
		y = height/2 - BALL_HEIGHT/2;
		xMove = (int) (Math.random()*2) + 1;
		yMove = (int) (Math.random()*2) + 1;
		if (d == 1) {
			xMove = - xMove;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getYMove() {
		return yMove;
	}
	
}
