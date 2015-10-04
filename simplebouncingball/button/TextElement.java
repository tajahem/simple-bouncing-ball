package simplebouncingball.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class TextElement {

	private Color textColor;
	private Font font;
	private String text;
	private int x, y;

	public TextElement(Color c, String t, Font f) {
		textColor = c;
		text = t;
		font = f;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public TextElement clone() {
		return new TextElement(textColor, text, font);
	}

	/**
	 * Clones the current TextElement with the supplied text color
	 * 
	 * @param c
	 * @return
	 */
	public TextElement clone(Color c) {
		return new TextElement(c, text, font);
	}

	/**
	 * Renders the text to the x and y coordinate supplied via the setPosition
	 * method. If setPosition has not been called it will render to 0,0.
	 * 
	 * @param g2d
	 */
	public void render(Graphics2D g2d) {
		g2d.setFont(font);
		g2d.setColor(textColor);
		g2d.drawString(text, x, y);
	}

	/**
	 * Attempts to render the text at the center of the supplied Rectangle. If
	 * the rectangle is not big enough it will overflow.
	 * 
	 * @param g2d
	 * @param bounds
	 */
	public void render(Graphics2D g2d, Rectangle bounds) {
		g2d.setFont(font);
		FontMetrics metrics = g2d.getFontMetrics();
		int textX = (int) (bounds.getCenterX() - metrics.stringWidth(text) / 2);
		int textY = (int) (bounds.getCenterY() + metrics.getHeight() / 2 - metrics.getDescent() / 2);
		g2d.drawString(text, textX, textY);
	}

}
