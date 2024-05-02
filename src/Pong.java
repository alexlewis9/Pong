/*Name: Sarah Sabaa & Irena Liu
 *Date: 5/20/2020
 *Rev: Production Release
 *Notes: This class extends JPanel and uses different movement detections to run the pong game.
 *
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pong extends JPanel implements ActionListener, KeyListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	public static JFrame window;
	
	static int width = 600;
	static int height = 400;

	final int MARGIN = 20;
	
	Font font = new Font("SansSerif", Font.PLAIN, 14);
	Font font2 = new Font("SansSerif", Font.PLAIN, 16);
	Font font3 = new Font("SansSerif", Font.PLAIN, 24);

	JButton button1 = new JButton("1 Player");
	JButton button2 = new JButton("2 Player");
	JButton button3 = new JButton("Keyboard");
	JButton button4 = new JButton("Mouse");
	JButton button5 = new JButton("Normal");
	JButton button6 = new JButton("Unbeatable");

	boolean playing = false;
	boolean chosenPlayers = false; // one or two players (one -> vs. computer)
	boolean chosenControl = false; // keys or mouse
	boolean chosenLevel = false;
	boolean gameOver = false;
	boolean newLongestMatch = false;

	enum Control {
		NONE,
		KEYS, // with computer
		MOUSE, // with computer
		TWOPLAYER //with keys
	}
	
	Control control = Control.NONE;
	
	int score1 = 0;
	int score2 = 0;
	
	enum Level {
		NONE,
		NORMAL,
		UNBEATABLE
	}
	
	Level level = Level.NONE;

	int maxScore = 10;
	int playingTime = 0;
	int longestMatch = 0;

	Ball ball = new Ball(width, height);
	Paddle player1;
	Paddle player2;

	Timer timer = new Timer(5, this);

	GameTime gameTime = new GameTime();

	public Pong() {
		window.setFocusable(true);
		window.addKeyListener(this);
		window.addMouseMotionListener(this);

		setFocusable(true);
		button1.addActionListener(this);
		add(button1);

		button2.addActionListener(this);
		add(button2);

		button3.addActionListener(this);
		add(button3);
		button3.setVisible(false);

		button4.addActionListener(this);
		add(button4);
		button4.setVisible(false);

		button5.addActionListener(this);
		add(button5);
		button5.setVisible(false);

		button6.addActionListener(this);
		add(button6);
		button6.setVisible(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		g.setFont(font);
		// text.getBounds();
		width = getWidth();
		height = getHeight();

		g.setColor(Color.WHITE);
		
		if (!playing && !gameOver) {
			setUpGamePaint(g);
		} else if (playing && !gameOver) {
			playingPaint(g);
		} else if (gameOver) {
			gameOverPaint(g);
		}

	}

	public static void main(String[] args) {
		window = new JFrame("Pong");
		window.setBounds(0, 0, width, height);
		// window.setPreferredSize(new Dimension(width, height));
		// window.getInsets();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Pong pong = new Pong();

		Container c = window.getContentPane();
		c.add(pong);
		window.setVisible(true);
	}

	public void updateGame() {
		updateScore();
		if (level == Level.UNBEATABLE) {
			playingTime = gameTime.getSeconds();
			if (playingTime >= longestMatch) {
				longestMatch = playingTime;
				newLongestMatch = true;
			}
		}
		if (score1 >= maxScore || score2 >= maxScore && !gameOver) {
			gameOver = true;
			timer.stop();
			gameTime.endTimer();
		}
	}
	
	public void updateScore() {
		if (reachedRight()) {
			if (hitPaddle(player1)) {
				ball.hitPaddle();
			} else {
				score2++;
				ball.serve(1);
			}
		}
		if (reachedLeft()) {
			if (hitPaddle(player2)) {
				ball.hitPaddle();
			} else {
				score1++;
				ball.serve(0);
			}
		}
	}
	
	public boolean reachedRight() {
		return ball.getX() >= width - player1.PADDLE_WIDTH - MARGIN;
	}
	
	public boolean reachedLeft() {
		return ball.getX() <= player2.PADDLE_WIDTH + MARGIN;
	}
	
	public boolean hitPaddle(Paddle paddle) {
		return ball.getY() >= paddle.y - ball.BALL_HEIGHT && ball.getY() <= paddle.y + player1.PADDLE_HEIGHT;
	}
	
	public void setUpGamePaint(Graphics g) {
		if (!playing && !chosenPlayers) {
			g.drawString("Select the number of players.", width / 2 - 100, height / 6);
		} else if (!playing && !chosenControl) {
			g.drawString("Choose your control type.", width / 2 - 80, height / 6);
		} else if (!playing && !chosenLevel) {
			g.drawString("Choose your level.", width / 2 - 80, height / 6);
		} else if (!playing) {
			g.setFont(font2);
			if (control == Control.TWOPLAYER) {
				g.drawString("Player 1: controls the left paddle with the \"A\" (up) and \"Z\" (down) keys.",
						width / 2 - 275, height / 6);
				g.drawString("Player 2: controls the right paddle with the \"K\" (up) and \"M\" (down) keys.",
						width / 2 - 275, height / 6 + 50);
			} else if (control == Control.MOUSE) {
				g.drawString("Move the right paddle up and down with the mouse.", width / 2 - 200, height / 6);
			} else if (control == Control.KEYS) {
				g.drawString("Control the right paddle with the \"K\" (up) and \"M\" (down) keys.", width / 2 - 250,
						height / 6);
			}
			g.drawString("First player to 10 points wins the game.", width / 2 - 150, height / 6 + 100);
			g.drawString("Press the spacebar to start.", width / 2 - 90, height / 6 + 150);
			timer.start();
		}
	}
	
	public void playingPaint(Graphics g) {
		ball.paint(g);
		player1.paint(g);
		player2.paint(g);
		g.setFont(font3);
		g.drawString("" + score2, width / 2 - MARGIN, 30);
		g.drawString("" + score1, width / 2 + MARGIN, 30);
		if (level == Level.UNBEATABLE) {
			g.setFont(font2);
			g.drawString("Playing time: " + playingTime, MARGIN / 2, height - MARGIN);
			g.drawString("Longest rally: " + longestMatch, width - 140, height - MARGIN);
		}
	}
	
	public void gameOverPaint(Graphics g) {
		g.setFont(font3);
		g.drawString("" + score2, width / 2 - MARGIN, 30);
		g.drawString("" + score1, width / 2 + MARGIN, 30);
		if (level == Level.UNBEATABLE) {
			g.drawString("Playing time: " + playingTime, width / 2 - 90, height / 6 + 50);
			g.drawString("Longest match: " + longestMatch, width / 2 - 90, height / 6 + 90);
			if (newLongestMatch) {
				g.drawString("NEW HIGH SCORE!!!", width / 2 - 110, height / 6 + 130);
			}
		} else {
			if (score1 > score2) {
				g.drawString("Player 2 wins.", width / 2 - 90, height / 4);
			} else {
				g.drawString("Player 1 wins.", width / 2 - 90, height / 4);
			}
		}
		g.drawString("Press the spacebar to play again.", width / 2 - 180, height / 6 + 170);
	}

	public void newGame() {
		playing = false;
		chosenPlayers = false;
		chosenControl = false;
		chosenLevel = false;
		gameOver = false;
		newLongestMatch = false;
		control = Control.NONE;
		level = Level.NONE;
		score1 = 0;
		score2 = 0;
		playingTime = 0;
		gameTime = new GameTime();
		button1.setVisible(true);
		button2.setVisible(true);
		repaint();
	}
	
	public void choosePlayers(JButton button) {
		if (button == button1) {
			button3.setVisible(true);
			button4.setVisible(true);
		} else if (button == button2) {
			player1 = new PlayerPaddleKeys(width, height, MARGIN, 1);
			player2 = new PlayerPaddleKeys(width, height, MARGIN, 2);
			control = Control.TWOPLAYER;
			chosenControl = true;
			chosenLevel = true;
		}
		chosenPlayers = true;
		button1.setVisible(false);
		button2.setVisible(false);
	}

	public void chooseControl(JButton button) {
		if (button == button3) {
			player1 = new PlayerPaddleKeys(width, height, MARGIN, 1);
			control = Control.KEYS;
		} else {
			player1 = new PlayerPaddleMouse(width, height, MARGIN);
			control = Control.MOUSE;
		}
		button3.setVisible(false);
		button4.setVisible(false);
		chosenControl = true;
		button5.setVisible(true);
		button6.setVisible(true);
	}

	public void chooseLevel(JButton button) {
		if (button == button5) {
			level = Level.NORMAL;
		} else {
			level = Level.UNBEATABLE;
		}
		int l;
		if (level == Level.NORMAL) {
			l = 1;
		} else {
			l = 2;
		}
		player2 = new ComputerPaddle(width, height, MARGIN, ball, l);
		button5.setVisible(false);
		button6.setVisible(false);
		chosenLevel = true;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (!chosenPlayers || !chosenControl || !chosenLevel) {
			JButton button = (JButton) e.getSource();
			if (!chosenPlayers) {
				choosePlayers(button);
			} else if (!chosenControl) {
				chooseControl(button);
			} else if (!chosenLevel) {
				chooseLevel(button);
			}
		}
		if (playing && !gameOver) {
			ball.move();
			player1.move(-1);
			player2.move(-1);
			updateGame();
		}
		repaint();

	}

	public void keyTyped(KeyEvent e) {
		// not used

	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		int d = -1;
		if (keyCode == KeyEvent.VK_SPACE && !playing && !gameOver) {
			playing = true;
			gameTime.start();
		} else if (keyCode == KeyEvent.VK_SPACE && gameOver) {
			newGame();
		}
		if (control == Control.KEYS || control == Control.TWOPLAYER) {
			if (keyCode == KeyEvent.VK_K) {
				d = 0;
				player1.pressed(d);
			} else if (keyCode == KeyEvent.VK_A) {
				d = 0;
				player2.pressed(d);
			} else if (keyCode == KeyEvent.VK_M) {
				d = 1;
				player1.pressed(d);
			} else if (keyCode == KeyEvent.VK_Z) {
				d = 1;
				player2.pressed(d);
			}
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (control == Control.KEYS || control == Control.TWOPLAYER) {
			if (keyCode == KeyEvent.VK_K || keyCode == KeyEvent.VK_M) {
				player1.released();
			} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_Z) {
				player2.released();
			}
		}
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
		if (control == Control.MOUSE) {
			int y = e.getY();
			player1.move(y);
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// not used
	}

}
