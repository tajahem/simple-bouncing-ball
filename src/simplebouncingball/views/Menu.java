package simplebouncingball.views;

import java.awt.Graphics2D;
import java.awt.Point;

import simplebouncingball.GameState;
import simplebouncingball.GameView;
import simplebouncingball.SimpleTextButton;
import simplebouncingball.config.Config;
import simplebouncingball.input.InputListener;
import simplebouncingball.input.InputType;

public class Menu implements GameView {

	private Config config;
	SimpleTextButton start;
	SimpleTextButton quit;

	// Just to display controls text
	SimpleTextButton controlsDisplay;
	private final char left = '\u21E6';
	private final char up = '\u21E7';
	private final char right = '\u21E8';
	private final char down = '\u21E9';
	private String controls = "Movement Controls: " + left + " " + up + " "
			+ right + " " + down;

	public Menu(Config config) {
		this.config = config;

		int buttonWidth = config.width / 3;
		int buttonHeight = (int) (config.textSize * 2.5);
		int buttonX = config.width / 2 - buttonWidth / 2;
		int firstY = buttonHeight * 2;
		start = new SimpleTextButton(buttonX, firstY, buttonWidth,
				buttonHeight, config, "START");
		quit = new SimpleTextButton(buttonX, firstY + buttonHeight * 2,
				buttonWidth, buttonHeight, config, "QUIT");
		controlsDisplay = new SimpleTextButton(config.width / 20, firstY
				+ buttonHeight * 4, config.width - config.width / 10,
				config.height - (firstY + buttonHeight * 6), config, controls);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(config.menuColor);
		g2d.fillRect(0, 0, config.width, config.height);
		start.render(g2d);
		quit.render(g2d);
		controlsDisplay.render(g2d);
	}

	@Override
	public GameState update(InputListener input) {
		if (input.hasMouseInput()) {
			Point p = input.getLastClick();
			if (start.contains(p)) {
				return GameState.MAIN;
			}
			if (quit.contains(p)) {
				return GameState.QUIT;
			}
		}
		if (input.activeInputs.contains(InputType.ESC)) {
			return GameState.QUIT;
		}
		return GameState.MENU;
	}

}
