// This class creates a computer paddle that moves in two different levels.

public class ComputerPaddle extends Paddle {

	private Ball ball;
	private int level;
	private int yMove;

	public ComputerPaddle(int width, int height, int margin, Ball ball, int level) {
		super(width, height, margin);
		x = 20;
		y = height/2 - PADDLE_HEIGHT/2;
		this.ball = ball;
		this.level = level;
	}

	public void move(int move) {
		if (level == 1) { //normal level
			if (ball.getYMove() > 0) {
				yMove =  (int) (Math.random()*2) + 1;
			} else {
				yMove =  -((int) (Math.random()*2) + 1);
			}
			y += yMove;		
		} else if (level == 2) { //unbeatable level
			y = ball.getY() - PADDLE_HEIGHT/2 + ball.BALL_HEIGHT/2;
		}
		if (y < 0) {
			y = 0;
		}
		if (y > this.getHeight() - 90) {
			y = this.getHeight() - 90;
		}
	}



}
