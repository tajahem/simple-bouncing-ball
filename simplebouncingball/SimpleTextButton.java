package simplebouncingball;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import simplebouncingball.config.Config;

/**
 * 
 * A simple flat button with text. Selection testing is done via the
 * contains(Point p) method.
 * 
 * @author tajahem
 *
 */
public class SimpleTextButton {

	private Rectangle bounds;
	private String name;
	private Font font;
	private Color bgColor;
	private Color textColor;

	public SimpleTextButton(int x, int y, int w, int h, Config c, String n) {
		bounds = new Rectangle(x, y, w, h);
		name = n;
		bgColor = c.menuColor;
		textColor = c.textColor;
		font = new Font(c.font, Font.BOLD, c.textSize);
	}

	public boolean contains(Point p) {
		return bounds.contains(p);
	}

	public void render(Graphics2D g2d) {
		g2d.setColor(bgColor);
		g2d.fill(bounds);
		g2d.setColor(textColor);
		g2d.draw(bounds);
		g2d.setFont(font);
		renderText(g2d);
	}

	// attempts to center the text in the middle of the button
	private void renderText(Graphics2D g2d) {
		FontMetrics metrics = g2d.getFontMetrics();
		// allows overflow if button is not large enough
		int textX = (int) (bounds.getCenterX() - metrics.stringWidth(name) / 2);
		int textY = (int) (bounds.getCenterY() + metrics.getHeight() / 2 - metrics
				.getDescent() / 2);
		g2d.drawString(name, textX, textY);
	}

	public void setColors(Color bg, Color text) {
		bgColor = bg;
		textColor = text;
	}

	public void setBGColor(Color c) {
		bgColor = c;
	}

	public void setTextColor(Color c) {
		textColor = c;
	}

}
