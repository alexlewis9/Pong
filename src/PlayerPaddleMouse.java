/*Name: Sarah Sabaa & Irena Liu
 *Date: 5/20/2020
 *Rev: Production Release
 *Notes: This class moves the player paddle based on the mouse movement.
 *
 */

public class PlayerPaddleMouse extends Paddle {

	public PlayerPaddleMouse(int width, int height, int margin) {
		super(width, height, margin);
	}

	public void move(int mouseY) {
		if (mouseY != -1) {
			y = mouseY - 30 - PADDLE_HEIGHT/2;
		}
		if (y < 0) {
			y = 0;
		}
		if (y > this.getHeight() - 90) {
			y = this.getHeight() - 90;
		}
	}
}
