package simplebouncingball.button.hover;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import simplebouncingball.button.TextElement;

public class OverlayColorEffect implements HoverEffect {

	private Rectangle border;
	private TextElement text;
	private Color color;

	public OverlayColorEffect(Rectangle r, Color c) {
		color = c;
		border = r;
	}

	public void setText(TextElement t) {
		text = t;
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.draw(border);
		if (text != null) {
			text.render(g2d, border);
		}
	}

}
