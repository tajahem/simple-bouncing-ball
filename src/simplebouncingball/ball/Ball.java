package simplebouncingball.ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

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
	private int renderSize;
	// position of the virtual coordinate space in relation to the view
	private Point position;
	// position of the ball in virtual coordinate space
	private Point ballPosition;
	private final Point lightSource;// intended for an eventual shadow effect
	private Velocity velocity;
	private Range range;

	// visual variables
	private Config config;
	private Color shadow = new Color(50, 50, 50, 25);
	private RoundingEffect rounding;
	private ColorManager color = new ColorManager();

	public Ball(Config c, Point l) {
		config = c;
		lightSource = l;
		renderSize = config.ballSize * 2;

		velocity = new Velocity(config.maxVelocity);
		rounding = new RoundingEffect(config);

		// start at center
		position = getCenter(config.width, config.height, renderSize);
		ballPosition = getCenter(renderSize, renderSize, config.ballSize);
		int modifier = (int) (config.ballSize * 1.5);
		range = new Range(config.width - modifier, -config.ballSize / 2);
	}

	private Point getCenter(int width, int height, int size) {
		return new Point(width / 2 - size / 2, height / 2 - size / 2);
	}

	private void move() {
		if (!range.isInRange(position.x, velocity.getX())) {
			velocity.reverseX();
		}
		if (!range.isInRange(position.y, velocity.getY())) {
			velocity.reverseY();
		}
		position.translate(velocity.getX(), velocity.getY());
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
		BufferedImage render = new BufferedImage(renderSize, renderSize,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = render.createGraphics();

		graphics.setColor(shadow);
		graphics.fillOval(ballPosition.x - 5, ballPosition.y - 5,
				config.ballSize + 10, config.ballSize + 10);
		graphics.drawImage(renderBall(), null, ballPosition.x, ballPosition.y);

		g2d.drawImage(render, null, position.x, position.y);
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
