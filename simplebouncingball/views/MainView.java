package simplebouncingball.views;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import simplebouncingball.GameState;
import simplebouncingball.GameView;
import simplebouncingball.ball.Ball;
import simplebouncingball.config.Config;
import simplebouncingball.input.InputListener;
import simplebouncingball.input.InputType;

public class MainView implements GameView {

	private Config config;
	Ball ball;

	public MainView(Config config) {
		this.config = config;
		Random random = new Random();
		ball = new Ball(config, new Point(random.nextInt(config.width),
				random.nextInt(config.height)));
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(config.background);
		g2d.fillRect(0, 0, config.width, config.height);
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
		ball.update(input);
		return GameState.MAIN;
	}

}
