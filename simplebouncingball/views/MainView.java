package simplebouncingball.views;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import simplebouncingball.GameState;
import simplebouncingball.GameView;
import simplebouncingball.ball.Ball;
import simplebouncingball.button.BorderedBackground;
import simplebouncingball.button.HoverableButton;
import simplebouncingball.button.TextElement;
import simplebouncingball.button.hover.OverlayColorEffect;
import simplebouncingball.config.Config;
import simplebouncingball.input.InputListener;
import simplebouncingball.input.InputType;

public class MainView implements GameView {

	private Config config;
	private Ball ball;
	private HoverableButton closeButton;

	public MainView(Config config) {
		this.config = config;
		ball = new Ball(config);
		Rectangle buttonPosition = new Rectangle(-2, -2, config.width / 20, config.ballSize / 2);
		TextElement text = new TextElement(config.textColor, "X", new Font(config.font, Font.BOLD, config.textSize));
		OverlayColorEffect effect = new OverlayColorEffect(buttonPosition, config.textColor);
		effect.setText(text);
		closeButton = new HoverableButton(buttonPosition, effect,
				new BorderedBackground(buttonPosition, config.background, config.shadow));
		closeButton.setText(text.clone(config.shadow));
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(config.background);
		g2d.fillRect(0, 0, config.width, config.height);
		closeButton.render(g2d);
		ball.render(g2d);
	}

	@Override
	public GameState update(InputListener input) {
		if (input.hasTypedInput()) {
			InputType last = input.getLastTyped();
			if (last == InputType.ESC) {
				return GameState.MENU;
			}
		}
		if (input.hasMouseInput()) {
			if (closeButton.isSelected(input.getLastClick())) {
				return GameState.MENU;
			}
		}
		closeButton.checkForHovered(input.mouseLocation);
		ball.update(input);
		return GameState.MAIN;
	}

}
