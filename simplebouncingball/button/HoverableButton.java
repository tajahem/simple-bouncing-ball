package simplebouncingball.button;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import simplebouncingball.button.hover.HoverEffect;

public class HoverableButton {

	private final Rectangle bounds;

	private final HoverEffect effect;
	private final ButtonBackground background;
	private TextElement text;
	private BufferedImage staticRender;

	private boolean hovered = false;

	public HoverableButton(Rectangle r, HoverEffect h, ButtonBackground b) {
		bounds = r;
		effect = h;
		background = b;
	}

	public void setText(TextElement t) {
		text = t;
	}

	public void checkForHovered(Point p) {
		hovered = bounds.contains(p);
	}

	public boolean isSelected(Point p) {
		return bounds.contains(p);
	}

	public void render(Graphics2D g2d) {
		if (staticRender == null) {
			createStaticRender();
		}
		BufferedImage render = new BufferedImage(bounds.width + 2,
				bounds.height + 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = render.createGraphics();
		graphics.drawImage(staticRender, null, 0, 0);
		if (hovered) {
			effect.render(graphics);
		}
		g2d.drawImage(render, null, bounds.x, bounds.y);
	}

	private void createStaticRender() {
		staticRender = new BufferedImage(bounds.width + 2, bounds.height + 2,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = staticRender.createGraphics();
		background.render(graphics);
		if (text != null) {
			text.render(graphics, new Rectangle(0, 0, bounds.width,
					bounds.height));
		}
	}

}
