package simplebouncingball.ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javafx.scene.shape.Ellipse;
import simplebouncingball.config.Config;
import simplebouncingball.input.InputListener;
import simplebouncingball.input.InputType;

//TODO implement shadow
public class Ball {

	/*
	 * In order to reduce the amount of nasty math required to position things
	 * correctly the ball utilizes BufferedImages to create virtual coordinate
	 * spaces to work in.
	 */
	private Rectangle renderArea;
	private Rectangle bounceArea;
	// position of the ball in virtual coordinate space
	private Point ballPosition;
	private final Point lightSource;// intended for an eventual shadow effect
	private Velocity velocity;

	private Rectangle bounds;

	// visual variables
	private Config config;
	private Color shadow = new Color(50, 50, 50, 50);
	private RoundingEffect rounding;
	private ColorManager color = new ColorManager();

	public Ball(Config c, Point l) {
		config = c;
		lightSource = l;

		velocity = new Velocity(config.maxVelocity);
		rounding = new RoundingEffect(config);

		// start at center
		Point initPosition = getCenter(config.width, config.height,
				config.ballSize * 2);
		renderArea = new Rectangle(initPosition.x, initPosition.y,
				config.ballSize * 2, config.ballSize * 2);
		ballPosition = getCenter(renderArea.width, renderArea.height,
				config.ballSize);
		bounds = new Rectangle(0, 0, config.width, config.height);
		int halfBall = config.ballSize / 2;
		bounceArea = new Rectangle(halfBall, halfBall, config.width
				- config.ballSize, config.height - config.ballSize);
	}

	private Point getCenter(int width, int height, int size) {
		return new Point(width / 2 - size / 2, height / 2 - size / 2);
	}

	private void move() {
		renderArea.translate(velocity.getX(), velocity.getY());
		if (!bounds.contains(renderArea)) {
			if (renderArea.x < bounds.x) {
				// should be negative so make it positive to get to bounds.x
				renderArea.translate(-renderArea.x, 0);
				velocity.reverseX();
			} else if (renderArea.getMaxX() > bounds.getMaxX()) {
				renderArea.translate(
						(int) (bounds.getMaxX() - renderArea.getMaxX()), 0);
				velocity.reverseX();
			}
			if (renderArea.y < bounds.y) {
				renderArea.translate(0, -renderArea.y);
				velocity.reverseY();
			} else if (renderArea.getMaxY() > bounds.getMaxY()) {
				renderArea.translate(0,
						(int) (bounds.getMaxY() - renderArea.getMaxY()));
				velocity.reverseY();
			}
		}
	}

	public void update(InputListener input) {
		for (InputType i : input.activeInputs) {
			switch (i) {
			case UP:
				velocity.modifyY(-1);
				break;
			case DOWN:
				velocity.modifyY(1);
				break;
			case LEFT:
				velocity.modifyX(-1);
				break;
			case RIGHT:
				velocity.modifyX(1);
				break;
			default:
				break;
			}
		}
		move();
	}

	public void render(Graphics2D g2d) {
		BufferedImage render = new BufferedImage(renderArea.width,
				renderArea.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = render.createGraphics();

		graphics.setColor(shadow);
		graphics.fillOval(ballPosition.x - 5, ballPosition.y - 5,
				config.ballSize + 10, config.ballSize + 10);
		graphics.drawImage(renderBall(), null, ballPosition.x, ballPosition.y);

		g2d.setColor(shadow);
		g2d.fill(bounceArea);
		g2d.drawImage(render, null, renderArea.x, renderArea.y);
	}

	private BufferedImage renderBall() {
		BufferedImage render = new BufferedImage(config.ballSize,
				config.ballSize, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = render.createGraphics();

		g2d.setColor(color.getColor());
		g2d.fillOval(1, 1, config.ballSize - 2, config.ballSize - 2);
		g2d.drawImage(rounding.getRounding(), null, 1, 1);
		return render;
	}

}
