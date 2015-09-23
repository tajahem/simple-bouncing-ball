package simplebouncingball.menu;

import java.awt.Graphics2D;
import java.awt.Point;

import simplebouncingball.GameState;
import simplebouncingball.GameView;
import simplebouncingball.SimpleTextButton;
import simplebouncingball.config.Config;
import simplebouncingball.input.InputHandler;
import simplebouncingball.input.Inputs;

public class Menu implements GameView {

	private Config config;
	SimpleTextButton start;
	SimpleTextButton quit;

	public Menu(Config config) {
		this.config = config;
		start = new SimpleTextButton(config.width / 2 - 50,
				config.height / 6 - 50, 100, 50, config, "START");
		quit = new SimpleTextButton(config.width / 2 - 50,
				config.height / 3 - 50, 100, 50, config, "QUIT");
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(config.menuColor);
		g2d.fillRect(0, 0, config.width, config.height);
		start.render(g2d);
		quit.render(g2d);
	}

	@Override
	public GameState update(InputHandler input) {
		if (input.hasMouseInput()) {
			Point p = input.getLastClick();
			if (start.contains(p)) {
				return GameState.NORMAL;
			}
			if (quit.contains(p)) {
				return GameState.QUIT;
			}
		}
		if (input.activeInputs.contains(Inputs.ESC)) {
			return GameState.QUIT;
		}
		return GameState.MENU;
	}

}
