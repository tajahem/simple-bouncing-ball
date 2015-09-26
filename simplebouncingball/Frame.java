package simplebouncingball;

import java.awt.Insets;

import javax.swing.JFrame;

import simplebouncingball.config.Config;
import simplebouncingball.input.InputListener;

/**
 * 
 * Frame serves as the program entry point and manages window creation and the
 * looping mechanism.
 * 
 * @author tajahem
 *
 */
public class Frame extends JFrame {

	private Config config;
	private InputListener input;
	private RenderPanel panel;

	public Frame() {
		super("Simple Bouncing Ball");
		config = new Config();
		panel = new RenderPanel(config);

		// setup window
		setSize(config.width, config.height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(panel);

		/*
		 * resize for window decoration which you can't know the size of until
		 * you've got a window
		 */
		Insets insets = getInsets();
		setSize(config.width + insets.left + insets.right, config.height
				+ insets.bottom + insets.top);

		input = new InputListener(this, panel);

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
