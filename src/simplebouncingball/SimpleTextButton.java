package simplebouncingball;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import simplebouncingball.config.Config;

public class SimpleTextButton {

	private Rectangle bounds;
	private Config config;
	private String name;
	private Font font;

	public SimpleTextButton(int x, int y, int w, int h, Config c, String n) {
		bounds = new Rectangle(x, y, w, h);
		config = c;
		name = n;
		font = new Font(config.font, Font.BOLD, config.textSize);
	}

	public boolean contains(Point p) {
		return bounds.contains(p);
	}

	public void render(Graphics2D g2d) {
		g2d.setColor(config.menuColor);
		g2d.fill(bounds);
		g2d.setColor(config.textColor);
		g2d.draw(bounds);
		g2d.setFont(font);
		renderText(g2d);
	}

	private void renderText(Graphics2D g2d) {
		FontMetrics metrics = g2d.getFontMetrics();
		// allows overflow if not careful
		int textX = (int) (bounds.getCenterX() - metrics.stringWidth(name) / 2) ;
		int textY = (int) (bounds.getCenterY() + metrics.getHeight() / 2 - metrics
				.getDescent() / 2);
		g2d.drawString(name, textX, textY);
	}

}
