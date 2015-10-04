package simplebouncingball.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BorderedBackground implements ButtonBackground {

	private Color background, border;
	private Rectangle bounds;
	
	public BorderedBackground(Rectangle r, Color background, Color border){
		this.background = background;
		this.border = border;
		bounds = r;
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(background);
		g2d.fill(bounds);
		g2d.setColor(border);
		g2d.draw(bounds);
	}

}
