package simplebouncingball.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import simplebouncingball.GameState;
import simplebouncingball.GameView;
import simplebouncingball.button.BorderedBackground;
import simplebouncingball.button.ButtonBackground;
import simplebouncingball.button.HoverableButton;
import simplebouncingball.button.SimpleTextButton;
import simplebouncingball.button.TextElement;
import simplebouncingball.button.hover.GlowEffect;
import simplebouncingball.config.Config;
import simplebouncingball.input.InputListener;
import simplebouncingball.input.InputType;

public class Menu implements GameView {

	private Config config;
	private HoverableButton start;
	private HoverableButton quit;

	// Just to display controls text
	private SimpleTextButton controlsDisplay;
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

		Rectangle elementSize = new Rectangle(0, 0, buttonWidth, buttonHeight);
		Color glowColor = new Color(config.textColor.getRed(),
				config.textColor.getGreen(), config.textColor.getBlue(), 100);
		GlowEffect glow = new GlowEffect(glowColor, elementSize);
		ButtonBackground background = new BorderedBackground(elementSize,
				config.menuColor, config.textColor);
		Font font = new Font(config.font, Font.BOLD, config.textSize);
		start = new HoverableButton(new Rectangle(buttonX, firstY, buttonWidth,
				buttonHeight), glow, background);
		start.setText(new TextElement(config.textColor, "START", font));
		quit = new HoverableButton(new Rectangle(buttonX, firstY + buttonHeight
				* 2, buttonWidth, buttonHeight), glow, background);
		quit.setText(new TextElement(config.textColor, "QUIT", font));

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
			if (start.isSelected(p)) {
				return GameState.MAIN;
			}
			if (quit.isSelected(p)) {
				return GameState.QUIT;
			}
		}
		if (input.activeInputs.contains(InputType.ESC)) {
			return GameState.QUIT;
		}
		if (input.mouseLocation != null) {
			start.checkForHovered(input.mouseLocation);
			quit.checkForHovered(input.mouseLocation);
		}
		return GameState.MENU;
	}

}
