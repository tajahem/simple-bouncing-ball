package simplebouncingball.button.hover;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;

public class GlowEffect implements HoverEffect {

	private RadialGradientPaint glow;
	private Rectangle bounds;

	public GlowEffect(Color glowColor, Rectangle bounds) {
		Color[] colors = { glowColor, new Color(0, 0, 0, 0) };
		float[] distribution = { 0f, 1f };
		float x = (float) bounds.getCenterX();
		float y = (float) bounds.getCenterY();
		float radius = (float) bounds.getWidth() / 2;
		glow = new RadialGradientPaint(x, y, radius, distribution, colors);
		this.bounds = bounds;
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setPaint(glow);
		g2d.fill(bounds);
	}

}
