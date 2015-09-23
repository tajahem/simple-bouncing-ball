package simplebouncingball;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import simplebouncingball.config.Config;
import simplebouncingball.input.InputHandler;
import simplebouncingball.menu.Menu;

public class RenderPanel extends JPanel {

	private Config config;
	private GameView view;
	private GameState currentState;

	public RenderPanel(Config c) {
		super(true);
		this.config = c;
		view = new Menu(config);
	}

	@Override
	public void paintComponent(Graphics g) {
		BufferedImage render = new BufferedImage(config.width, config.height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = render.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		view.render(g2d);

		// render to parent
		g.drawImage(render, 0, 0, null);
	}

	public boolean update(InputHandler input) {
		GameState updatedState = view.update(input);
		if (updatedState == GameState.QUIT) {
			return false;
		}
		if (updatedState != currentState) {
			currentState = updatedState;
			changeState();
		}
		return true;
	}

	private void changeState() {
		//TODO
	}

}
