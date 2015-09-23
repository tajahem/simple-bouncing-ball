package simplebouncingball;

import java.awt.Graphics2D;

import simplebouncingball.input.InputHandler;

public interface GameView {

	public void render(Graphics2D g2d);

	public GameState update(InputHandler input);

}
