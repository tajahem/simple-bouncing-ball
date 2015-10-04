package simplebouncingball.ball;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import simplebouncingball.config.Config;

/**
 * 
 * Generates a BufferedImage with a series of semi-transparent circles of
 * decreasing size that create a rounding effect when layered over the ball.
 * 
 * @author tajahem
 *
 */
public class RoundingEffect {

	private BufferedImage rounding;

	public RoundingEffect(Config config) {
		createRounding(config);
	}

	private void createRounding(Config config) {
		rounding = new BufferedImage(config.ballSize, config.ballSize,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rounding.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(config.highlight);
		for (int n = 2; n < config.ballSize; n += 2) {
			g2d.fillOval(n, n, config.ballSize - n * 2, config.ballSize - n * 2);
		}
		g2d.setColor(config.shadow);
		for(int n =1; n < 3;n++){
			g2d.setStroke(new BasicStroke(n));
			g2d.drawOval(0, 0, config.ballSize - 2, config.ballSize - 2);
		}
	}

	public BufferedImage getRounding() {
		return rounding;
	}

}
