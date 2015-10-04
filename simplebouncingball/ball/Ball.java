package simplebouncingball.ball;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import simplebouncingball.config.Config;
import simplebouncingball.input.InputListener;
import simplebouncingball.input.InputType;

/**
 * 
 * A player controlled ball
 * 
 * @author tajahem
 *
 */
public class Ball {

	// rectangle to hold the virtual coordinate space the ball will be drawn in
	private Rectangle renderArea;

	// max range of the renderArea
	private Rectangle bounds;

	// position of the ball in virtual coordinate space
	private Point ballPosition;

	private Velocity velocity;

	// visual variables
	private Config config;

	private RoundingEffect rounding;

	private ColorManager colorManager = new ColorManager();

	public Ball(Config c) {
		config = c;

		velocity = new Velocity(config.maxVelocity);
		rounding = new RoundingEffect(config);

		// start at center
		Point initPosition = getCenter(config.width, config.height, config.ballSize * 2);
		renderArea = new Rectangle(initPosition.x, initPosition.y, config.ballSize * 2, config.ballSize * 2);
		ballPosition = getCenter(renderArea.width, renderArea.height, config.ballSize);
		int halfBallSize = config.ballSize / 2;
		bounds = new Rectangle(-halfBallSize, -halfBallSize, config.width + config.ballSize,
				config.height + config.ballSize);
	}

	private Point getCenter(int width, int height, int size) {
		return new Point(width / 2 - size / 2, height / 2 - size / 2);
	}

	private void move() {
		renderArea.translate(velocity.getX(), velocity.getY());
		if (!bounds.contains(renderArea)) {
			// change x variable
			if (renderArea.x < bounds.x) {
				renderArea.x = bounds.x;
				velocity.reverseX();
			} else if (renderArea.getMaxX() > bounds.getMaxX()) {
				renderArea.x = (int) (bounds.getMaxX() - renderArea.width);
				velocity.reverseX();
			}
			// change y variable
			if (renderArea.y < bounds.y) {
				renderArea.y = bounds.y;
				velocity.reverseY();
			} else if (renderArea.getMaxY() > bounds.getMaxY()) {
				renderArea.y = (int) (bounds.getMaxY() - renderArea.height);
				velocity.reverseY();
			}
		}
	}

	/**
	 * Update modifies the velocity depending upon which directions are active
	 * 
	 * @param input
	 */
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
		BufferedImage render = new BufferedImage(renderArea.width, renderArea.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = render.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw the ball
		graphics.drawImage(renderBall(), null, ballPosition.x, ballPosition.y);

		g2d.drawImage(render, null, renderArea.x, renderArea.y);
	}

	private BufferedImage renderBall() {
		BufferedImage render = new BufferedImage(config.ballSize, config.ballSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = render.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(colorManager.getColor());
		g2d.fillOval(1, 1, config.ballSize - 2, config.ballSize - 2);
		g2d.drawImage(rounding.getRounding(), null, 1, 1);
		return render;
	}

}
