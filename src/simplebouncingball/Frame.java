package simplebouncingball;

import java.awt.Insets;

import javax.swing.JFrame;

import simplebouncingball.config.Config;
import simplebouncingball.input.InputHandler;

public class Frame extends JFrame {

	private Config config;
	private InputHandler input;
	private RenderPanel panel;

	public Frame() {
		super("Simple Bouncing Ball");
		config = new Config();
		setSize(config.width, config.height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		panel = new RenderPanel(config);
		add(panel);
		input = new InputHandler(this, panel);

		runloop();
	}

	private void runloop() {
		boolean running = true;
		while (running) {
			running = panel.update(input);
			panel.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}// eat for now
		}
		System.exit(0);
	}

	public static void main(String[] args) {
		new Frame();
	}

}
